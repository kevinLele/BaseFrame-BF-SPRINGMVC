package com.hq.cloudplatform.baseframe.entity;

import com.hq.cloudplatform.baseframe.utils.validation.annotation.ValidationBean;

/**
 * @author Administrator
 * @date 7/24/2017
 */
@ValidationBean
public class FileInfo extends BaseEntity {

    /**
     * 文件名
     */
    private String fileName;

    private String md5;

    private String size;

    /**
     * 文件的URL地址
     */
    private String url;

    /**
     * 对应到上传文件的相关信息
     */
    private UploadFileInfo uploadFileInfo = new UploadFileInfo();

    public UploadFileInfo getUploadFileInfo() {
        return uploadFileInfo;
    }

    public void setUploadFileInfo(UploadFileInfo uploadFileInfo) {
        this.uploadFileInfo = uploadFileInfo;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
