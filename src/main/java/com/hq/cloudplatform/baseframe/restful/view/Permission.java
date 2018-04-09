package com.hq.cloudplatform.baseframe.restful.view;

import com.hq.cloudplatform.baseframe.entity.BaseEntity;

/**
 * Created by Administrator on 2017/3/10.
 * 权限属性表实体类
 */
public class Permission extends BaseEntity {

    private String parentId;

    private String name;

    private String code;

    private String type;

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
