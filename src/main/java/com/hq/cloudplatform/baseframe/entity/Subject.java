package com.hq.cloudplatform.baseframe.entity;


import com.hq.cloudplatform.baseframe.utils.validation.annotation.ValidationBean;
import com.hq.cloudplatform.baseframe.utils.validation.annotation.ValidationField;

import java.io.Serializable;
import java.util.List;

@ValidationBean
public class Subject extends BaseEntity implements Serializable {

    /**
     * 专题名称
     * 字段类型: varchar
     * 长度: 50
     */
    @ValidationField(notNull = true)
    private String name;

    /**
     * 专题描述信息
     * 字段类型: varchar
     * 长度: 500
     */
    private String description;

    /**
     * 专题图标
     * 字段类型: longtext
     */
    private String icon;

    /**
     * 是否有效:启用/停用
     * 字段类型: int
     * 长度: 11
     */
    private String isValid;

    private List<Resource> resourceList;

    private List<SubjectAuthority> subjectAuthorityList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public List<Resource> getResourceList() {
        return resourceList;
    }

    public void setResourceList(List<Resource> resourceList) {
        this.resourceList = resourceList;
    }

    public List<SubjectAuthority> getSubjectAuthorityList() {
        return subjectAuthorityList;
    }

    public void setSubjectAuthorityList(List<SubjectAuthority> subjectAuthorityList) {
        this.subjectAuthorityList = subjectAuthorityList;
    }

}