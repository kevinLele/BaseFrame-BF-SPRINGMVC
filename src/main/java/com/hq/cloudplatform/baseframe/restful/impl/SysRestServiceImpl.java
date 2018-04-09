package com.hq.cloudplatform.baseframe.restful.impl;

import com.alibaba.fastjson.JSON;
import com.hq.cloudplatform.baseframe.exception.ServiceException;
import com.hq.cloudplatform.baseframe.restful.ISysRestService;
import com.hq.cloudplatform.baseframe.restful.view.ResultBean;
import com.hq.cloudplatform.baseframe.restful.view.User;
import com.hq.cloudplatform.baseframe.sys.Constants;
import com.hq.cloudplatform.baseframe.utils.rest.RestUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@Slf4j
public class SysRestServiceImpl implements ISysRestService {

    @Autowired
    @Lazy
    protected HttpServletRequest request;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 此方法只是用于调试登陆
     *
     * @return
     */
    @Override
    public ResultBean login(@RequestBody User user) {
        //模拟登陆
        HttpSession session = request.getSession();

        ResultBean jsonObj = restTemplate.getForObject(
                RestUtils.getCAServerUrl("/public/user/getByLoginName?loginName={loginName}"),
                ResultBean.class, user.getLoginName());

        user = JSON.parseObject(jsonObj.getContentAsStr(), User.class);
        session.setAttribute(Constants.SESSION_KEY_USER, user);

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getLoginName(), "");
        subject.login(token);
        ResultBean jsonView = new ResultBean();
        jsonView.successPack(user);

        return jsonView;
    }

    @Override
    public String logout() throws ServiceException {
        ResultBean jsonView = new ResultBean();
        Subject subject = SecurityUtils.getSubject();

        try {
            subject.logout();
            request.getSession().removeAttribute(Constants.SESSION_KEY_USER);
            jsonView.successPack("success");
        } catch (AuthenticationException e) {
            jsonView.failPack("用户退出异常，请重试");
        }

        return JSON.toJSONStringWithDateFormat(jsonView, "yyyy-MM-dd HH:mm:ss");
    }

    @Override
    public String getCurrentUserPermissions() throws ServiceException {
        User user = (User) request.getSession().getAttribute(Constants.SESSION_KEY_USER);

        return restTemplate.getForObject(
                RestUtils.getCAServerUrl("/public/permission/getAllByLoginName?loginName={loginName}"),
                String.class,
                user.getLoginName());
    }

    @Override
    public String getCurrentUser() throws ServiceException {
        ResultBean jsonView = new ResultBean();
        User user = (User) request.getSession().getAttribute(Constants.SESSION_KEY_USER);

        if (user != null) {
            jsonView.successPack(user);
        }

        return JSON.toJSONStringWithDateFormat(jsonView, "yyyy-MM-dd HH:mm:ss");
    }

    @Override
    public String getCurrentUserPermissionsTree() throws ServiceException {
        User user = (User) request.getSession().getAttribute(Constants.SESSION_KEY_USER);

        return restTemplate.getForObject(
                RestUtils.getCAServerUrl("/public/permission/getAllForTreeByLoginName?loginName={loginName}&appCode={appCode}"),
                String.class,
                user.getLoginName(),
                Constants.APP_CODE);
    }


}
