package com.hq.cloudplatform.baseframe.service;

import com.hq.cloudplatform.baseframe.exception.ServiceException;

import java.util.Map;

/**
 * 流程变量初始化器
 *
 * @author Administrator
 */
public interface ProcessStartInitiator {

    Map<String, Object> createProcessVariables(String businessId) throws ServiceException;
}
