package com.hq.cloudplatform.baseframe.restful;

import com.hq.cloudplatform.baseframe.exception.ServiceException;
import com.hq.cloudplatform.baseframe.restful.view.ResultBean;
import com.hq.cloudplatform.baseframe.restful.view.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 7/4/2017.
 */
@RequestMapping("/api/sys")
@Api(tags = "系统常用基础接口", description = "用户登陆以及获取用户权限相关的接口")
public interface ISysRestService {

    @PostMapping(
            value = "login",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "用户登陆", notes = "loginName是必须要传入的")
    ResultBean login(User user) throws ServiceException;

    @GetMapping(
            value = "logout",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    String logout() throws ServiceException;

    @GetMapping(
            value = "getCurrentUserPermissions",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    String getCurrentUserPermissions() throws ServiceException;

    @GetMapping(
            value = "getCurrentUser",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    String getCurrentUser() throws ServiceException;

    @GetMapping(
            value = "getCurrentUserPermissionsTree",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    String getCurrentUserPermissionsTree() throws ServiceException;
}
