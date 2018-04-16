package com.hq.cloudplatform.baseframe.service;

import com.hq.cloudplatform.baseframe.entity.UploadFileInfo;
import com.hq.cloudplatform.baseframe.exception.ServiceException;

/**
 * Created by Administrator on 2017/4/27.
 */
public interface IFileUploadService extends IBaseService<UploadFileInfo> {

    /**
     * 通过KEY来查找上传的文件信息
     *
     * @param key
     * @return
     * @throws ServiceException
     */
    UploadFileInfo findByKey(String key) throws ServiceException;

    /**
     * 通过KEY来删除上传的文件信息
     *
     * @param key
     * @return
     * @throws ServiceException
     */
    boolean deleteByKey(String key) throws ServiceException;
}
