package com.hq.cloudplatform.baseframe.sys.aspect.log;

import com.hq.cloudplatform.baseframe.utils.json.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author Administrator
 * @date 7/4/2017
 */
@Slf4j
public class LogMethodInterceptor implements MethodInterceptor {

    @Autowired
    @Lazy
    private HttpServletRequest request;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        printMethodParams(invocation.getThis().getClass(),
                invocation.getMethod(),
                invocation.getArguments());

        return invocation.proceed();
    }

    /**
     * 打印类method的名称以及参数
     *
     * @param cls
     * @param method
     * @param args
     */
    public void printMethodParams(Class cls, Method method, Object[] args) {
        //获取方法参数名称
        String[] paramNames = getFieldsName(method);

        //定义目标类的日志
        log.info("-------------------------------");
        log.info("clsName = {}", cls.getName());
        log.info("methodName = {}", method.getName());
        logParam(log, paramNames, args);
        log.info("-------------------------------");
    }

    /**
     * 获取方法参数名称
     *
     * @param method
     * @return
     * @throws Exception
     */
    private String[] getFieldsName(Method method) {
        ParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();

        return parameterNameDiscoverer.getParameterNames(method);
    }

    /**
     * 打印方法参数值  基本类型直接打印，非基本类型需要重写toString方法
     *
     * @param paramsArgsName  方法参数名数组
     * @param paramsArgsValue 方法参数值数组
     */
    private void logParam(Logger log, String[] paramsArgsName, Object[] paramsArgsValue) {
        if (ArrayUtils.isEmpty(paramsArgsName) || ArrayUtils.isEmpty(paramsArgsValue)) {
            log.info("该方法没有参数");
            return;
        }

        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < paramsArgsName.length; i++) {
            //参数名
            String name = paramsArgsName[i];

            //参数值
            Object value = paramsArgsValue[i];
            buffer.append(name).append(" = ");

            if (isPrimite(value.getClass())) {
                buffer.append(value + "  ,");
            } else {
                buffer.append(JacksonUtil.toJSONString(value) + "  ,");
            }
        }

        if (buffer.length() > 0) {
            //去掉最后一个多余的逗号
            buffer.deleteCharAt(buffer.length() - 1);
        }

        log.info(buffer.toString());
    }

    /**
     * 判断是否为基本类型：包括String
     *
     * @param clazz clazz
     * @return true：是;     false：不是
     */
    private boolean isPrimite(Class<?> clazz) {
        if (clazz.isPrimitive() || clazz == String.class) {
            return true;
        } else {
            return false;
        }
    }
}
