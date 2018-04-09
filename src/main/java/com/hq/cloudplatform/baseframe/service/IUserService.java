package com.hq.cloudplatform.baseframe.service;


import com.hq.cloudplatform.baseframe.exception.ServiceException;
import com.hq.cloudplatform.baseframe.restful.view.User;

import java.util.Set;

/**
 *
 * @author admin
 * @date 2017/3/7
 */
public interface IUserService extends IBaseService<User> {

    /**
     * 通过登录名查询用户信息
     *
     * @param name
     * @return
     * @throws ServiceException
     */
    User findByLoginName(String name) throws ServiceException;

    /**
     * 通过登录名查询用户信息
     *
     * @param username
     * @return
     * @throws ServiceException
     */
    Set<String> getRoleStringsByUserName(String username) throws ServiceException;
}
