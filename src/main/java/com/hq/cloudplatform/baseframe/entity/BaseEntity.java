package com.hq.cloudplatform.baseframe.entity;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author Administrator
 */
public class BaseEntity {

    @ApiModelProperty(value = "主键ID 字段类型: varchar 长度: 32")
    private String id;

    @ApiModelProperty(value = "创建时间 字段类型: datetime")
    private Date createDate;

    @ApiModelProperty(value = "创建者的姓名 字段类型: varchar 长度: 30")
    private String creator;

    @ApiModelProperty(value = "创建者的ID 字段类型: varchar 长度: 32")
    private String creatorId;

    @ApiModelProperty(value = "最后修改时间 字段类型: datetime")
    private Date updateDate;

    @ApiModelProperty(value = "修改者的姓名 字段类型: varchar 长度: 30")
    private String updater;

    @ApiModelProperty(value = "修改者的ID 字段类型: varchar 长度: 32")
    private String updaterId;

    @ApiModelProperty(value = "是否删除 字段类型: int 说明: 0表示未删除、1表示已删除")
    private String isDelete;

    @ApiModelProperty(value = "删除时间（逻辑删除） 字段类型: datetime")
    private Date deleteDate;

    @ApiModelProperty(value = "删除者的姓名 字段类型: varchar 长度: 30")
    private String deleter;

    @ApiModelProperty(value = "删除者的ID 字段类型: varchar 长度: 32")
    private String deleterId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public String getUpdaterId() {
        return updaterId;
    }

    public void setUpdaterId(String updaterId) {
        this.updaterId = updaterId;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public Date getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(Date deleteDate) {
        this.deleteDate = deleteDate;
    }

    public String getDeleter() {
        return deleter;
    }

    public void setDeleter(String deleter) {
        this.deleter = deleter;
    }

    public String getDeleterId() {
        return deleterId;
    }

    public void setDeleterId(String deleterId) {
        this.deleterId = deleterId;
    }
}
