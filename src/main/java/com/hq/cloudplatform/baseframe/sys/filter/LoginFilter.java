package com.hq.cloudplatform.baseframe.sys.filter;

import com.hq.cloudplatform.baseframe.restful.view.ResultBean;
import com.hq.cloudplatform.baseframe.restful.view.User;
import com.hq.cloudplatform.baseframe.sys.Constants;
import com.hq.cloudplatform.baseframe.utils.ConfigHelper;
import com.hq.cloudplatform.baseframe.utils.json.JacksonUtil;
import com.hq.cloudplatform.baseframe.utils.rest.RestUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * @author Administrator
 * @date 2015/11/26 0026
 */
public class LoginFilter implements Filter {

    private Pattern pattern;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String ignoreUrl = filterConfig.getInitParameter("ignoreUrl");
        pattern = Pattern.compile(ignoreUrl);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse rsp = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        String username = request.getRemoteUser();
        String requestPath = request.getServletPath();
        boolean isUseCAS = Boolean.parseBoolean(ConfigHelper.getValue("CAS.isOpen"));

        //设置不缓存响应头信息，强制浏览器不缓存
        rsp.setHeader("Cache-Control", "no-cache");
        rsp.setHeader("Cache-Control", "no-store");
        rsp.setHeader("Pragma", "no-cache");
        rsp.setDateHeader("Expires", 0);

        //不需要处理的静态文件直接忽略
        if (pattern.matcher(requestPath).find()) {
            chain.doFilter(servletRequest, servletResponse);
            return;
        }

        User user = (User) session.getAttribute(Constants.SESSION_KEY_USER);

        //用户
        if (null == user && isUseCAS) {
            //从CA系统获取当前用户的登陆信息并存在SESSION中
            ResultBean jsonObj = restTemplate.getForObject(
                    RestUtils.getCAServerUrl("/public/user/getByLoginName?loginName={loginName}"),
                    ResultBean.class,
                    username);
            user = JacksonUtil.parseObject(jsonObj.getContentAsStr(), User.class);
            session.setAttribute(Constants.SESSION_KEY_USER, user);

            //通过模拟登陆操作实现shiro的登陆
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(username, "");
            subject.login(token);
        }

        chain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
