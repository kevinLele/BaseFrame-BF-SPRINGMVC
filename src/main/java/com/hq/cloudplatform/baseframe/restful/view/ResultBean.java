package com.hq.cloudplatform.baseframe.restful.view;


import com.alibaba.fastjson.annotation.JSONField;

/**
 * restful对外的JSON 对象封装
 *
 * @author Administrator
 */
public class ResultBean<Entity> {

    /**
     * 成功
     */
    public static final String SUCCESS = "success";

    /**
     * 失败
     */
    public static final String FAIL = "fail";

    /**
     * 校验失败
     */
    public static final String CHECK_FAIL = "check_fail";

    /**
     * 未认证（即未登陆系统）
     */
    public static final String UNAUTHENTICATED = "unauthenticated";

    /**
     * 未授权(即登陆成功但没有相关操作权限)
     */
    public static final String UNAUTHORIZED = "unauthorized";

    /**
     * 未知的异常
     */
    public static final String UNKNOWN_EXCEPTION = "unknown_error";

    /**
     * 状态值
     */
    private String status = SUCCESS;

    /**
     * 返回的编码
     */
    private String code;

    /**
     * 返回的消息描述
     */
    private String message;

    /**
     * 返回的内容
     */
    private Entity content;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getContent() {
        return content;
    }

    @JSONField(serialize = false)
    public String getContentAsStr() {
        if (null == content) {
            return "";
        } else {
            return content.toString();
        }
    }

    public void setContent(Entity content) {
        this.content = content;
    }

    public static <Entity> ResultBean successPack(Entity content) {
        ResultBean<Entity> resultBean = new ResultBean<>();
        resultBean.setMessage("");
        resultBean.setContent(content);
        resultBean.setStatus(SUCCESS);

        return resultBean;
    }

    public static <Entity> ResultBean successPack(Entity content, String msg) {
        ResultBean<Entity> resultBean = new ResultBean<>();
        resultBean.setContent(content);
        resultBean.setMessage(msg);
        resultBean.setStatus(SUCCESS);

        return resultBean;
    }

    public static <Entity> ResultBean failPack(Exception e) {
        ResultBean<Entity> resultBean = new ResultBean<>();
        String message = e.getMessage();
        int index = message.indexOf(":");
        resultBean.setMessage(index == -1 ? message : message.substring(index + 1));
        resultBean.setStatus(FAIL);

        return resultBean;
    }

    public static <Entity> ResultBean failPack(String errMsg) {
        ResultBean<Entity> resultBean = new ResultBean<>();
        resultBean.setMessage(errMsg);
        resultBean.setStatus(FAIL);

        return resultBean;
    }

    /**
     * 未认证的响应结果
     * （未认证表示未进行登陆，无法获取用户的信息）
     *
     * @return
     */
    public static <Entity> ResultBean unauthenticatedPack() {
        ResultBean<Entity> resultBean = new ResultBean<>();
        resultBean.setMessage("未认证，请先登陆系统！");
        resultBean.setStatus(UNAUTHENTICATED);

        return resultBean;
    }

    /**
     * 未授权的响应结果
     * （未授权表示已登陆，已确认用户的身份，只是没有某项操作的权限）
     *
     * @return
     */
    public static <Entity> ResultBean unauthorizedPack() {
        ResultBean<Entity> resultBean = new ResultBean<>();
        resultBean.setMessage("您无权进行该操作！");
        resultBean.setStatus(UNAUTHORIZED);

        return resultBean;
    }

    public static <Entity> ResultBean failPack(Entity content) {
        ResultBean<Entity> resultBean = new ResultBean<>();
        resultBean.setContent(content);
        resultBean.setStatus(FAIL);

        return resultBean;
    }

    public static <Entity> ResultBean failPackMessage(Entity content, String errMsg) {
        ResultBean<Entity> resultBean = new ResultBean<>();
        resultBean.setMessage(errMsg);
        resultBean.setContent(content);
        resultBean.setStatus(FAIL);

        return resultBean;
    }


}
