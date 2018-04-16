package com.hq.cloudplatform.baseframe.restful.impl;

import com.hq.cloudplatform.baseframe.entity.ChunkFileInfo;
import com.hq.cloudplatform.baseframe.entity.FileInfo;
import com.hq.cloudplatform.baseframe.exception.ServiceException;
import com.hq.cloudplatform.baseframe.restful.IFileUploadRestService;
import com.hq.cloudplatform.baseframe.restful.view.ResultBean;
import com.hq.cloudplatform.baseframe.service.IFileInfoService;
import com.hq.cloudplatform.baseframe.service.impl.FileInfoServiceImpl;
import com.hq.cloudplatform.baseframe.sys.Constants;
import com.hq.cloudplatform.baseframe.utils.json.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.channels.FileChannel;
import java.util.*;

/**
 * @author Administrator
 * @date 7/20/2017
 */
@RestController
@Slf4j
public class FileUploadRestServiceImpl implements IFileUploadRestService {

    @Autowired
    private IFileInfoService fileInfoService;

    @Autowired
    private HttpServletRequest request;

    /**
     * 检查上传的文件是否已上传过
     * 判断的标准备是文件的MD5值和大小都一致则认为是同一个文件,返回文件已存在的响应
     *
     * @param fileInfo
     * @return
     */
    @Override
    public ResultBean checkFile(@RequestBody FileInfo fileInfo) {

        String fileName = fileInfo.getFileName();
        String md5 = fileInfo.getMd5();
        String size = fileInfo.getSize();
        String fileKey = fileInfoService.generateKey(fileName, md5, size);
        Map<String, Object> content = new HashMap<>(2);

        try {
            //检查上传的文件是否已上传过
            boolean isExist = fileInfoService.checkFileIsExist(fileKey);

            if (isExist) {
                content.put("isExist", true);

                //通过文件名称和文件的唯一标识来查找文件的信息
                FileInfo fileInfoEntity = fileInfoService.getByKeyAndName(fileKey, fileName);

                //如果未找到则进行添加， 此种情况出现的原因是上传的文件已存在（即MD5和大小一致），只有文件名变化了
                if (null == fileInfoEntity) {
                    String fileUploadId = fileInfoService.getUploadFileIdByKey(fileKey);
                    String fileId = fileInfoService.generateUUID();

                    fileInfoEntity = new FileInfo();
                    fileInfoEntity.setId(fileId);
                    fileInfoEntity.setFileName(fileName);
                    fileInfoEntity.getUploadFileInfo().setId(fileUploadId);
                    fileInfoEntity.setUrl(getFileUrl(fileId));

                    fileInfoService.save(fileInfoEntity);
                }

                content.put("url", fileInfoEntity.getUrl());
            } else {
                content.put("isExist", false);

                //如果是部分上传了则返回当前的上传进度
                content.put(FileInfoServiceImpl.PERCENT_KEY, fileInfoService.getUploadedPercent(fileKey));
            }

            return ResultBean.successPack(content);
        } catch (ServiceException e) {
            return ResultBean.failPackWithMessage("服务接口异常!", e);
        }
    }

    /**
     * 检查该分块文件是否已上传
     *
     * @param chunkFileInfo
     * @return
     */
    @Override
    public ResultBean checkChunk(@RequestBody ChunkFileInfo chunkFileInfo) {

        String fileName = chunkFileInfo.getFileName();
        String fileMd5 = chunkFileInfo.getFileMd5();
        String fileSize = chunkFileInfo.getFileSize();
        String chunkNum = chunkFileInfo.getChunkNum();
        long chunkSize = chunkFileInfo.getChunkSize();
        String fileKey = fileInfoService.generateKey(fileName, fileMd5, fileSize);
        Map<String, Object> content = new HashMap<>(1);

        try {
            Boolean isExist = fileInfoService.checkChunkIsExist(fileKey, chunkNum, chunkSize);

            if (isExist) {
                content.put("isExist", true);
            } else {
                content.put("isExist", false);
            }

            return ResultBean.successPack(content);
        } catch (ServiceException serviceException) {
            return ResultBean.failPackWithMessage(serviceException.getMessage(), serviceException);
        }
    }

    /**
     * 保存上传的文件分块
     *
     * @param filePart
     * @param fileName
     * @param chunk
     * @param fileSize
     * @param fileMd5
     * @param percent
     * @return
     */
    @Override
    public String uploadChunk(@RequestParam("file") MultipartFile filePart,
                              @RequestParam("name") String fileName,
                              @RequestParam("chunk") String chunk,
                              @RequestParam("size") String fileSize,
                              @RequestParam("fileMd5") String fileMd5,
                              @RequestParam("percent") String percent) {
        String fileKey = fileInfoService.generateKey(fileName, fileMd5, fileSize);

        try {
            File tempDir = new File(Constants.UPLOADER_TEMP_DIR, fileKey);

            if (!tempDir.exists()) {
                tempDir.mkdir();
            }

            //把表单内容转换成流
            InputStream fileInputStream = filePart.getInputStream();
            File chunkFile = new File(tempDir, chunk);

            //保存文件
            FileUtils.copyInputStreamToFile(fileInputStream, chunkFile);

            //保存成功后在Redis中缓存相应的记录,方便记录上传的完成比列
            fileInfoService.cacheChunkInfoByRedis(fileKey, chunk, percent);

            return JacksonUtil.toJSONString(
                    ResultBean.successPack("success"));
        } catch (IOException e) {
            log.error("文件写入失败!", e);

            return JacksonUtil.toJSONString(
                    ResultBean.failPackWithMessage("文件写入失败!", e));
        } catch (RedisConnectionFailureException redisConnectionException) {
            log.error("Redis服务器异常!", redisConnectionException);

            return JacksonUtil.toJSONString(
                    ResultBean.failPackWithMessage("Redis服务器异常!", redisConnectionException));
        }
    }

    /**
     * 对文件分片进行合并
     *
     * @param chunkFileInfo
     * @return
     */
    @Override
    public ResultBean mergeChunk(@RequestBody ChunkFileInfo chunkFileInfo) {
        String name = chunkFileInfo.getFileName();
        String md5 = chunkFileInfo.getFileMd5();
        String size = chunkFileInfo.getFileSize();
        String type = chunkFileInfo.getFileType();
        String fileKey = fileInfoService.generateKey(name, md5, size);

        File tempDir = new File(Constants.UPLOADER_TEMP_DIR, fileKey);
        File prodDir = new File(Constants.UPLOADER_PROD_DIR);

        //读取目录里的所有文件, 排除目录只要文件
        File[] fileArray = tempDir.listFiles(pathname -> !pathname.isDirectory());

        //转成集合，便于排序
        List<File> fileList = Arrays.asList(fileArray);
        fileList.sort(Comparator.comparing(f -> Integer.valueOf(f.getName())));

        //合并后的文件
        File outputFile = new File(prodDir, fileKey);
        FileChannel outChannel = null;

        try {
            //创建文件
            outputFile.createNewFile();

            //输出流
            outChannel = new FileOutputStream(outputFile).getChannel();

            //合并
            FileChannel inChannel = null;

            for (File file : fileList) {
                try {
                    inChannel = new FileInputStream(file).getChannel();
                    inChannel.transferTo(0, inChannel.size(), outChannel);
                } finally {
                    if (null != inChannel) {
                        inChannel.close();
                    }
                }

                //删除分片
                file.delete();
            }

            //清除文件所使用的临时文件夹(用于存放分块文件的目录)
            if (tempDir.isDirectory() && tempDir.exists()) {
                tempDir.delete();
            }

            //合并成功后生成文件访问的URL, 并将文件KEY(MD5,Size)以及URL存入数据库以及Redis中
            FileInfo fileInfo = new FileInfo();
            String fileId = fileInfoService.generateUUID();

            fileInfo.setId(fileId);
            fileInfo.setFileName(name);
            fileInfo.setUrl(getFileUrl(fileId));
            fileInfo.getUploadFileInfo().setKey(fileKey);
            fileInfo.getUploadFileInfo().setFilePath(outputFile.getCanonicalPath());
            fileInfo.getUploadFileInfo().setContentType(type);
            fileInfo.getUploadFileInfo().setSize(Integer.parseInt(size));

            fileInfoService.save(fileInfo);

            //将文件的信息缓存到Redis中
            fileInfoService.cacheFileInfoByRedis(fileInfo);

            Map<String, Object> content = new HashMap<>(1);
            content.put("url", fileInfo.getUrl());
            return ResultBean.successPack(content);
        } catch (IOException e) {
            log.error("文件合并失败!", e);
            return ResultBean.failPackWithMessage("文件合并失败!", e);
        } catch (ServiceException e) {
            log.error("数据库异常!", e);
            return ResultBean.failPackWithMessage("数据库异常!", e);
        } finally {
            if (null != outChannel) {
                try {
                    outChannel.close();
                } catch (IOException e) {
                    log.error("输出流关闭异常!", e);
                }
            }
        }
    }

    private String getFileUrl(String fileId) {
        int serverPort = request.getServerPort();
        String ServerPath = request.getContextPath();

        return fileInfoService.getFileUrlById(fileId, serverPort, ServerPath);
    }
}
