package com.hq.cloudplatform.baseframe.sys.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * @author Administrator
 * @date 10/16/2017
 */
@Slf4j
public class RequireHttpsProcessingFilter implements Filter {

    public static final int HTTP_STANDARD_PORT = 80;

    public static final String HTTP_SCHEMA = "http";

    public static final int HTTPS_STANDARD_PORT = 443;

    public static final String HTTPS_SCHEMA = "https";

    private Pattern pattern;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String urlPrefix = filterConfig.getInitParameter("UrlPrefix");
        pattern = Pattern.compile(urlPrefix);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String queryString = request.getQueryString();
        String schema = request.getScheme();
        String pathInfo = request.getPathInfo();
        String contextPath = request.getContextPath();

        String destination = request.getServletPath() + ((pathInfo == null) ? "" : pathInfo)
                + ((queryString == null) ? "" : ("?" + queryString));

        // 符合过滤条件的http请求必须以https协议进行访问
        if (HTTP_SCHEMA.equals(schema.toLowerCase()) && pattern.matcher(destination).find()) {
            //获取系统配置的https协议端口，默认是8443
            Integer redirectPort = 8443;

            boolean includePort = redirectPort.intValue() != HTTPS_STANDARD_PORT;
            String redirectUrl = HTTPS_SCHEMA + "://" + request.getServerName()
                    + ((includePort) ? (":" + redirectPort) : "")
                    + contextPath + destination;

            log.debug("Redirect Url: " + redirectUrl);

            //执行重定向操作
            response.sendRedirect(response.encodeRedirectURL(redirectUrl));
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
