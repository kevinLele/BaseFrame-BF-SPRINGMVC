package com.hq.cloudplatform.baseframe.entity;


import com.hq.cloudplatform.baseframe.utils.dictionary.annotation.DictionaryField;
import com.hq.cloudplatform.baseframe.utils.validation.annotation.ValidationBean;
import com.hq.cloudplatform.baseframe.utils.validation.annotation.ValidationField;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ValidationBean
public class Resource extends BaseEntity implements Serializable {

    /**
     * 资源名称
     * 字段类型: varchar
     * 长度: 100
     */
    @ValidationField(notNull = true, regex = ".*{2,100}", tip = "资源名称长度必须大于2位长度")
    private String name;

    /**
     * 资源类型
     * 字段类型: varchar
     * 长度: 20
     */
    @DictionaryField("res_type")
    private String type;

    /**
     * 资源属性ID
     * 字段类型: varchar
     * 长度: 32
     */
    private String propertyId;

    /**
     * 资源元数据ID
     * 字段类型: varchar
     * 长度: 32
     */
    private String metadataId;

    /**
     * 资源的URL
     * 字段类型: varchar
     * 长度: 200
     */
    @ValidationField(notNull = true, tip = "不合法的URL地址")
    private String url;

    /**
     * 资源图标
     * 字段类型: longtext
     */
    private String icon;

    /**
     * 是否使用代理
     * 字段类型: int
     * 长度: 11
     */
    private String useProxy;

    /**
     * 代理地址
     * 字段类型: varchar
     * 长度: 200
     */
    private String proxyUrl;

    /**
     * 是否使用缓存
     * 字段类型: int
     * 长度: 11
     */
    private String useCache;

    /**
     * 缓存配置ID
     * 字段类型: varchar
     * 长度: 32
     */
    private String cacheConfigId;

    /**
     * 资源所属组织机构ID
     * 字段类型: varchar
     * 长度: 32
     */
    private String orgId;

    /**
     * 备注
     * 字段类型: varchar
     * 长度: 500
     */
    private String remark;

    /**
     * 是否需要审核
     * 字段类型: int
     * 长度: 11
     */
    private String needAudit;

    /**
     * 审核状态
     * 字段类型: varchar
     * 长度: 20
     */
    private String auditStatus;

    /**
     * 审核时间
     * 字段类型: datetime
     */
    private Date auditDate;

    /**
     * 审核人ID
     * 字段类型: varchar
     * 长度: 32
     */
    private String auditorId;

    /**
     * 审核人姓名
     * 字段类型: varchar
     * 长度: 30
     */
    private String auditor;

    /**
     * 审核意见
     * 字段类型: varchar
     * 长度: 500
     */
    private String auditOpinion;

    private Integer applyNumber;

    private List<Subject> subjectList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public String getMetadataId() {
        return metadataId;
    }

    public void setMetadataId(String metadataId) {
        this.metadataId = metadataId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUseProxy() {
        return useProxy;
    }

    public void setUseProxy(String useProxy) {
        this.useProxy = useProxy;
    }

    public String getProxyUrl() {
        return proxyUrl;
    }

    public void setProxyUrl(String proxyUrl) {
        this.proxyUrl = proxyUrl;
    }

    public String getUseCache() {
        return useCache;
    }

    public void setUseCache(String useCache) {
        this.useCache = useCache;
    }

    public String getCacheConfigId() {
        return cacheConfigId;
    }

    public void setCacheConfigId(String cacheConfigId) {
        this.cacheConfigId = cacheConfigId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getNeedAudit() {
        return needAudit;
    }

    public void setNeedAudit(String needAudit) {
        this.needAudit = needAudit;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Date getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(Date auditDate) {
        this.auditDate = auditDate;
    }

    public String getAuditorId() {
        return auditorId;
    }

    public void setAuditorId(String auditorId) {
        this.auditorId = auditorId;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public String getAuditOpinion() {
        return auditOpinion;
    }

    public void setAuditOpinion(String auditOpinion) {
        this.auditOpinion = auditOpinion;
    }

    public List<Subject> getSubjectList() {
        return subjectList;
    }

    public void setSubjectList(List<Subject> subjectList) {
        this.subjectList = subjectList;
    }

    public Integer getApplyNumber() {
        return applyNumber;
    }

    public void setApplyNumber(Integer applyNumber) {
        this.applyNumber = applyNumber;
    }
}