package com.hq.cloudplatform.baseframe.entity;


import com.hq.cloudplatform.baseframe.utils.dictionary.annotation.DictionaryField;
import com.hq.cloudplatform.baseframe.utils.validation.annotation.ValidationBean;

import java.io.Serializable;
import java.util.Date;

@ValidationBean
public class SubjectAuthority extends BaseEntity implements Serializable {

    /**
     * 专题ID
     * 字段类型: varchar
     * 长度: 32
     */
    private String subjectId;

    /**
     * 授权对象类型: 用户/角色/机构
     * 字段类型: varchar
     * 长度: 20
     */
    @DictionaryField("target_type")
    private String targetType;

    /**
     * 授权对象ID:用户ID/角色ID/机构ID
     * 字段类型: varchar
     * 长度: 32
     */
    private String targetId;

    /**
     * 授权时间
     * 字段类型: datetime
     */
    private Date authorizeDate;

    /**
     * 授权人ID
     * 字段类型: varchar
     * 长度: 32
     */
    private String authorizerId;

    /**
     * 授权人姓名
     * 字段类型: varchar
     * 长度: 30
     */
    private String authorizer;

    /**
     * 是否有效: 启用/停用
     * 字段类型: int
     * 长度: 11
     */
    private String isValid;

    private Subject subject;

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public Date getAuthorizeDate() {
        return authorizeDate;
    }

    public void setAuthorizeDate(Date authorizeDate) {
        this.authorizeDate = authorizeDate;
    }

    public String getAuthorizerId() {
        return authorizerId;
    }

    public void setAuthorizerId(String authorizerId) {
        this.authorizerId = authorizerId;
    }

    public String getAuthorizer() {
        return authorizer;
    }

    public void setAuthorizer(String authorizer) {
        this.authorizer = authorizer;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

}