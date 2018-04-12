package com.hq.cloudplatform.baseframe.sys.filter;

import org.apache.shiro.web.servlet.AbstractFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Administrator
 */
public class CrossDomainFilter extends AbstractFilter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse rsp = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;

        //允许跨域的响应头设置
        rsp.setHeader("Access-Control-Allow-Credentials", "true");
        rsp.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));
        rsp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        rsp.setHeader("Access-Control-Max-Age", "3600");
        rsp.setHeader("Access-Control-Allow-Headers", "Content-Type");

        chain.doFilter(request, rsp);
    }
}
