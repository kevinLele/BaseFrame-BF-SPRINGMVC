package com.hq.cloudplatform.baseframe.restful.impl;

import com.hq.cloudplatform.baseframe.entity.FileInfo;
import com.hq.cloudplatform.baseframe.exception.ServiceException;
import com.hq.cloudplatform.baseframe.restful.IFileDownloadRestService;
import com.hq.cloudplatform.baseframe.service.IFileInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author Administrator
 * @date 7/20/2017
 */
@RestController
@Slf4j
public class FileDownloadRestServiceImpl implements IFileDownloadRestService {

    @Autowired
    private IFileInfoService fileInfoService;

    /**
     * 根据文件ID下载文件
     *
     * @param fileId
     * @return
     */
    @Override
    public ResponseEntity download(@PathVariable("fileId") String fileId) {
        try {
            FileInfo fileInfo = fileInfoService.findById(fileId);
            File file = new File(fileInfo.getUploadFileInfo().getFilePath());
            String fileName = URLEncoder.encode(fileInfo.getFileName(), "UTF-8");
            String contentType = fileInfo.getUploadFileInfo().getContentType();
            HttpHeaders headers = new HttpHeaders();

            //图片不需要设置该响应头
            if (!contentType.startsWith("image")) {
                headers.setContentDispositionFormData("attachment", fileName);
            }

            headers.setContentType(MediaType.valueOf(contentType));
            headers.setContentLength(file.length());
            headers.setCacheControl("no-cache");

            return new ResponseEntity<>(FileUtils.readFileToByteArray(file),
                    headers, HttpStatus.OK);
        } catch (UnsupportedEncodingException e) {
            log.error("文件名编码错误", e);

            return new ResponseEntity("文件名编码错误", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            log.error("文件不存在", e);

            return new ResponseEntity("文件不存在", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ServiceException e) {
            log.error("FileDownloadRestServiceImpl download is error,{fileId:" + fileId + "}", e);

            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
