package com.hq.cloudplatform.baseframe.sys.realm;

import com.hq.cloudplatform.baseframe.exception.ServiceException;
import com.hq.cloudplatform.baseframe.restful.view.User;
import com.hq.cloudplatform.baseframe.sys.Constants;
import com.hq.cloudplatform.baseframe.utils.rest.RestUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * 用于CAS单点登陆系统的Realm
 */
@Slf4j
public class CasRealm extends AuthorizingRealm {

    @Autowired
    @Lazy
    private HttpServletRequest request;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 授权操作，决定那些角色可以使用那些资源
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //null usernames are invalid
        if (principals == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }

        String username = (String) getAvailablePrincipal(principals);

        try {
            return getAuthorizationInfo(username);
        } catch (ServiceException e) {
            throw new AuthorizationException(e);
        }
    }

    private SimpleAuthorizationInfo getAuthorizationInfo(String username) throws ServiceException {
        User user = (User) request.getSession().getAttribute(Constants.SESSION_KEY_USER);

        Set<String> roleSet = restTemplate.getForObject(
                RestUtils.getCAServerUrl("/public/role/getAllByLoginName?loginName={loginName}"),
                Set.class,
                user.getLoginName()
        );

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roleSet);

        Set<String> permissionSet = restTemplate.getForObject(
                RestUtils.getCAServerUrl("/public/permission/getAllByLoginName?loginName={loginName}"),
                Set.class,
                user.getLoginName());

        info.setStringPermissions(permissionSet);

        return info;
    }

    /**
     * 认证操作，判断一个请求是否被允许进入系统
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();

        /*
         * 此处不做用户有效性验证, 因为验证过程已交由CAS单点登陆平台完成
         */

        // Null username is invalid
        if (username == null) {
            throw new AccountException("Null usernames are not allowed by this realm.");
        }

        return new SimpleAuthenticationInfo(username, "", getName());
    }
}
