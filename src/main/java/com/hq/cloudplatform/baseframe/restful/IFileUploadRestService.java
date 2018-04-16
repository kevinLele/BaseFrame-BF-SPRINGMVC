package com.hq.cloudplatform.baseframe.restful;

import com.hq.cloudplatform.baseframe.entity.ChunkFileInfo;
import com.hq.cloudplatform.baseframe.entity.FileInfo;
import com.hq.cloudplatform.baseframe.restful.view.ResultBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Administrator
 * @date 7/20/2017
 */
@RequestMapping("/api/fileUpload")
@Api(tags = "文件上传", description = "文件上传相关的接口")
public interface IFileUploadRestService {

    /**
     * 对文件进行校验，判断文件是否已上传过
     *
     * @param fileInfo
     * @return
     */
    @PostMapping(
            value = "/checkFile",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "检查文件是否存在")
    ResultBean checkFile(FileInfo fileInfo);

    /**
     * 对文件分片进行校验
     *
     * @param chunkFileInfo
     * @return
     */
    @PostMapping(
            value = "/checkChunk",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "检查文件分片是否存在")
    ResultBean checkChunk(ChunkFileInfo chunkFileInfo);

    /**
     * 上传文件分片
     *
     * @param filePart
     * @param fileName
     * @param chunk
     * @param fileSize
     * @param fileMd5
     * @param percent
     * @return
     */
    @PostMapping(
            value = "/uploadChunk",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE)
    @ApiOperation(value = "上传一个分片")
    String uploadChunk(MultipartFile filePart,
                       String fileName,
                       String chunk,
                       String fileSize,
                       String fileMd5,
                       String percent);

    /**
     * 对文件分片进行合并
     *
     * @param chunkFileInfo
     * @return
     */
    @PostMapping(
            value = "/mergeChunk",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "合并或有文件分片")
    ResultBean mergeChunk(ChunkFileInfo chunkFileInfo);
}
