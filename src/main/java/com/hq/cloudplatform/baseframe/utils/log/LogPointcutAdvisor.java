package com.hq.cloudplatform.baseframe.utils.log;

import org.aopalliance.aop.Advice;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.List;

/**
 *
 * @author Administrator
 * @date 7/4/2017
 */
public class LogPointcutAdvisor extends StaticMethodMatcherPointcutAdvisor {

    private List<String> basePackages;

    @Autowired
    @Lazy
    private HttpServletRequest request;

    public LogPointcutAdvisor(Advice advice, List<String> basePackages) {
        setAdvice(advice);
        this.basePackages = basePackages;
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        if (null == targetClass.getPackage()) {
            return false;
        }

        String packagePath = targetClass.getPackage().getName();

        for (String basePkg : basePackages) {
            if (packagePath.startsWith(basePkg)) {
                return true;
            }
        }

        return false;
    }
}
