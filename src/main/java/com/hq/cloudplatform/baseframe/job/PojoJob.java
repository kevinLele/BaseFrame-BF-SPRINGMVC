package com.hq.cloudplatform.baseframe.job;

import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 用于演示作业类不继承特定基类，使用MethodInvokeJobDetailFactoryBean
 * @author Administrator
 */
@Service("pojoJob")
public class PojoJob {

    public void doSomething() {
        System.out.println(new Date() + " com.hq.cloudplatform.baseframe.job.PojoJob : doSomething...");
    }
}
