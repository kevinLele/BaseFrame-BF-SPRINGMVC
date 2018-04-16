package com.hq.cloudplatform.baseframe.service.impl;

import com.hq.cloudplatform.baseframe.dao.BaseDAO;
import com.hq.cloudplatform.baseframe.dao.UploadFileInfoDAO;
import com.hq.cloudplatform.baseframe.entity.UploadFileInfo;
import com.hq.cloudplatform.baseframe.exception.ServiceException;
import com.hq.cloudplatform.baseframe.service.IFileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 * @date 2017/4/27
 */
@Service
public class FileUploadServiceImpl extends BaseServiceImpl<UploadFileInfo> implements IFileUploadService {

    @Autowired
    private UploadFileInfoDAO uploadFileInfoDAO;

    @Override
    public BaseDAO<UploadFileInfo> getBaseMapper() {
        return uploadFileInfoDAO;
    }

    @Override
    public UploadFileInfo findByKey(String key) throws ServiceException {
        UploadFileInfo entity;

        try {
            entity = this.uploadFileInfoDAO.findByKey(key);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }

        return entity;
    }

    @Override
    public boolean deleteByKey(String key) throws ServiceException {
        try {
            this.uploadFileInfoDAO.deleteByKey(key);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }

        return true;
    }
}
