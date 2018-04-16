package com.hq.cloudplatform.baseframe.sys.aspect;

import com.hq.cloudplatform.baseframe.exception.CheckException;
import com.hq.cloudplatform.baseframe.exception.ServiceException;
import com.hq.cloudplatform.baseframe.exception.UnloginException;
import com.hq.cloudplatform.baseframe.restful.view.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @author Administrator
 */
@Slf4j
public class ControllerAspect {

    public Object handlerControllerMethod(ProceedingJoinPoint pjp) {
        long startTime = System.currentTimeMillis();

        ResultBean<?> result;

        try {
            result = (ResultBean<?>) pjp.proceed();
            log.info("Method: [{}] use time: {}", pjp.getSignature(), System.currentTimeMillis() - startTime);
        } catch (Throwable e) {
            result = handlerException(pjp, e);
        }

        return result;
    }

    private ResultBean<?> handlerException(ProceedingJoinPoint pjp, Throwable e) {
        ResultBean<String> result = new ResultBean();
        result.setStatus(ResultBean.FAIL);
        result.setMessage(e.getLocalizedMessage());
        log.error(pjp.getSignature() + " error!", e);

        // 已知异常
        if (e instanceof ServiceException) {
            result.setContent("服务接口异常");
        } else if (e instanceof CheckException) {
            result.setContent("校验不通过");
        } else if (e instanceof UnloginException) {
            result.setContent("未登陆");
        } else if (e instanceof UnauthorizedException) {
            result.setContent("未授权");
        } else {
            result.setContent("未知异常");
        }

        return result;
    }
}
