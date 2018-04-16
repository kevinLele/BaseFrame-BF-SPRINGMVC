package com.hq.cloudplatform.baseframe.dao;

import com.hq.cloudplatform.baseframe.entity.UploadFileInfo;
import org.springframework.stereotype.Repository;

/**
 * Created by admin on 2017/3/7.
 */
@Repository("uploadFileInfoDAO")
public interface UploadFileInfoDAO extends BaseDAO<UploadFileInfo> {

    UploadFileInfo findByKey(String key);

    void deleteByKey(String key);
}
