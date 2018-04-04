package com.hq.CloudPlatform.BaseFrame.restful;

import com.hq.CloudPlatform.BaseFrame.exception.ServiceException;
import com.hq.CloudPlatform.BaseFrame.restful.view.User;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 7/4/2017.
 */
@RequestMapping("/api/sys")
public interface ISysRestService {

    @PostMapping(
            value = "login",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    String login(User user) throws ServiceException;

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
