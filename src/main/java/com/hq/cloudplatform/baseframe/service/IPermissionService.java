package com.hq.cloudplatform.baseframe.service;


import com.hq.cloudplatform.baseframe.exception.ServiceException;
import com.hq.cloudplatform.baseframe.restful.view.Permission;

import java.util.Set;

/**
 *
 * @author admin
 * @date 2017/3/7
 */
public interface IPermissionService extends IBaseService<Permission> {
    /**
     * 通过登录名查询该用户下的所有权限
     *
     * @param loginName
     * @return
     * @throws ServiceException
     */
    Set<String> getPermissionStringsByLoginName(String loginName) throws ServiceException;

}
