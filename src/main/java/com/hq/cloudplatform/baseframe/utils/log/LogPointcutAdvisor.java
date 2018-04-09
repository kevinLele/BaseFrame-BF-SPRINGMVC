package com.hq.cloudplatform.baseframe.utils.log;

import org.aopalliance.aop.Advice;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 7/4/2017.
 */
public class LogPointcutAdvisor extends StaticMethodMatcherPointcutAdvisor {

    private String basePackage;

    @Autowired
    @Lazy
    private HttpServletRequest request;

    public LogPointcutAdvisor(Advice advice, String basePackage) {
        setAdvice(advice);
        this.basePackage = basePackage;
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        if(null == targetClass.getPackage()){
            return false;
        }

        String packagePath = targetClass.getPackage().getName();

        if (packagePath.startsWith(basePackage)) {
            return true;
        } else {
            return false;
        }
    }
}
