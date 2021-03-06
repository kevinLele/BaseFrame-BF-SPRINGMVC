package com.hq.cloudplatform.baseframe.exception;

/**
 * 服务接口异常
 * @author Administrator
 */
public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = 819257588855553664L;

    public ServiceException(Exception e) {
        super(e);
    }

    public ServiceException(String msg, Exception e) {
        super(msg, e);
    }

    public ServiceException(String msg) {
        super(msg);
    }
}
