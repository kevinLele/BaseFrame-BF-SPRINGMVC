package com.hq.CloudPlatform.BaseFrame.sys.realm;

import com.hq.CloudPlatform.BaseFrame.exception.ServiceException;
import com.hq.CloudPlatform.BaseFrame.restful.view.User;
import com.hq.CloudPlatform.BaseFrame.service.IPermissionService;
import com.hq.CloudPlatform.BaseFrame.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

/**
 * Created by Administrator on 2017/3/9.
 */
@Slf4j
public class MybatisRealm extends AuthorizingRealm {

    @Autowired(required = false)
    @Lazy
    private IUserService userService;

    @Autowired(required = false)
    @Lazy
    private IPermissionService permissionService;

    /**
     * 授权操作，决定那些角色可以使用那些资源
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //null username are invalid
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
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(userService.getRoleStringsByUserName(username));
        info.setStringPermissions(permissionService.getPermissionStringsByLoginName(username));

        return info;
    }

    /**
     * 认证操作，判断一个请求是否被允许进入系统
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();
        User user;

        // Null username is invalid
        if (username == null) {
            throw new AccountException("Null usernames are not allowed by this realm.");
        }
        try {
            user = userService.findByLoginName(username);
        } catch (Exception e) {
            final String message = "There was a SQL error while authenticating user [" + username + "]";

            if (log.isErrorEnabled()) {
                log.error(message, e);
            }

            // Rethrow any SQL errors as an authentication exception
            throw new AuthenticationException(message, e);
        }

        if (null == user) {
            throw new UnknownAccountException("No account found for user [" + username + "]");
        }

        /* 逻辑说明：
         * 当将从数据库中读取的用户名和密码返回给Shiro框架后，Shiro会与调用subject.login方法时传入
         * 的用户名和密码对比，如果与数据库中读取的相同则认为登陆成功，代码如下：
         *     UsernamePasswordToken token = new UsernamePasswordToken(user.getLoginName(), user.getPassword)
         *     subject.login(token)
         */
        return new SimpleAuthenticationInfo(username, user.getPassword(), getName());
    }
}
