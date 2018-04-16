package com.hq.cloudplatform.baseframe.restful;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 7/20/2017.
 */
@RequestMapping("/api/fileDownload")
@Api(tags = "文件下载", description = "文件下载相关的接口")
public interface IFileDownloadRestService {

    @GetMapping(
            value = "download/{fileId}",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ApiOperation(value = "根据fileId下载文件")
    ResponseEntity<byte[]> download(@PathVariable("fileId") String fileId);
}
