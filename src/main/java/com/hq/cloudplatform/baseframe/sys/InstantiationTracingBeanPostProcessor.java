package com.hq.cloudplatform.baseframe.sys;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 *
 * @author Administrator
 * @date 7/3/2017
 */
@Component
@Slf4j
public class InstantiationTracingBeanPostProcessor implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        File tempDir = new File(Constants.UPLOADER_TEMP_DIR);
        File prodDir = new File(Constants.UPLOADER_PROD_DIR);

        if (!tempDir.exists()) {
            tempDir.mkdirs();
        }

        if (!prodDir.exists()) {
            prodDir.mkdirs();
        }

        log.info("系统初始化完成...");
    }
}
