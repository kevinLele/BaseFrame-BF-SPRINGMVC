package com.hq.cloudplatform.baseframe.restful.view;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hq.cloudplatform.baseframe.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 */
@ApiModel(value = "user对象", description = "用户对象user")
public class User extends BaseEntity {

    private String orgId;

    private String roleId;

    @ApiModelProperty(value = "用户登陆帐号", name = "loginName", example = "zhangsan")
    private String loginName;

    private String password;

    private String username;

    private String jobNum;

    private String loginIp;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastLoginDate;

    private Organization organization;

    private List<Role> roleList;

    private Integer isLock;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date loceDate;

    private Integer tryCount;

    /**
     * 手机号码
     */
    private String phone;

    private String email;

    private String directManagerId;

    public String getDirectManagerId() {
        return directManagerId;
    }

    public void setDirectManagerId(String directManagerId) {
        this.directManagerId = directManagerId;
    }

    public String getDirectManagerName() {
        return directManagerName;
    }

    public void setDirectManagerName(String directManagerName) {
        this.directManagerName = directManagerName;
    }

    private String directManagerName;


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public Integer getIsLock() {
        return isLock;
    }

    public void setIsLock(Integer isLock) {
        this.isLock = isLock;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getJobNum() {
        return jobNum;
    }

    public void setJobNum(String jobNum) {
        this.jobNum = jobNum;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public Date getLoceDate() {
        return loceDate;
    }

    public void setLoceDate(Date loceDate) {
        this.loceDate = loceDate;
    }

    public Integer getTryCount() {
        return tryCount;
    }

    public void setTryCount(Integer tryCount) {
        this.tryCount = tryCount;
    }
}
