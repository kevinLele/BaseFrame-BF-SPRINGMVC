package com.hq.cloudplatform.baseframe.job;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

/**
 * @author Administrator
 */
public class PrintMessage extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        // 获取参数
        JobDataMap mergedJobDataMap = context.getMergedJobDataMap();
        String name = (String) mergedJobDataMap.get("name");
        System.out.println(new Date() + " com.hq.cloudplatform.baseframe.job.PrintMessage: 定时任务执行中 ......" + "传递的参数为" + name);
    }
}
