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
    private String name;

    /**
     * 文件的URL地址
     */
    private String url;

    /**
     * 对应到上传文件的相关信息
     */
    private UploadFileInfo uploadFileInfo;

    public UploadFileInfo getUploadFileInfo() {
        return uploadFileInfo;
    }

    public void setUploadFileInfo(UploadFileInfo uploadFileInfo) {
        this.uploadFileInfo = uploadFileInfo;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
