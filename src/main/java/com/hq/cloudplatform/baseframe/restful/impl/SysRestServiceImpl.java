package com.hq.cloudplatform.baseframe.restful.impl;

import com.hq.cloudplatform.baseframe.restful.ISysRestService;
import com.hq.cloudplatform.baseframe.restful.view.ResultBean;
import com.hq.cloudplatform.baseframe.restful.view.User;
import com.hq.cloudplatform.baseframe.sys.Constants;
import com.hq.cloudplatform.baseframe.utils.json.JacksonUtil;
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

/**
 * @author Administrator
 */
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

        user = JacksonUtil.parseObject(jsonObj.getContentAsStr(), User.class);
        session.setAttribute(Constants.SESSION_KEY_USER, user);

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getLoginName(), "");
        subject.login(token);

        return ResultBean.successPack(user);
    }

    @Override
    public ResultBean logout() {
        Subject subject = SecurityUtils.getSubject();

        try {
            subject.logout();
            request.getSession().removeAttribute(Constants.SESSION_KEY_USER);
            return ResultBean.successPack("success");
        } catch (AuthenticationException e) {
            return ResultBean.failPack("用户退出异常，请重试");
        }
    }

    @Override
    public ResultBean getCurrentUserPermissions() {
        User user = (User) request.getSession().getAttribute(Constants.SESSION_KEY_USER);

        return restTemplate.getForObject(
                RestUtils.getCAServerUrl("/public/permission/getAllByLoginName?loginName={loginName}"),
                ResultBean.class,
                user.getLoginName());
    }

    @Override
    public ResultBean getCurrentUser() {
        User user = (User) request.getSession().getAttribute(Constants.SESSION_KEY_USER);

        if (user != null) {
            return ResultBean.successPack(user);
        } else {
            return ResultBean.failPackWithMessage("Not Login!");
        }
    }

    @Override
    public ResultBean getCurrentUserPermissionsTree() {
        User user = (User) request.getSession().getAttribute(Constants.SESSION_KEY_USER);

        return restTemplate.getForObject(
                RestUtils.getCAServerUrl("/public/permission/getAllForTreeByLoginName?loginName={loginName}&appCode={appCode}"),
                ResultBean.class,
                user.getLoginName(),
                Constants.APP_CODE);
    }


}
