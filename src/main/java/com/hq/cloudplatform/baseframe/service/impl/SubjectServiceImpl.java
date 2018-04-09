package com.hq.cloudplatform.baseframe.service.impl;

import com.hq.cloudplatform.baseframe.entity.Subject;
import com.hq.cloudplatform.baseframe.exception.ServiceException;
import com.hq.cloudplatform.baseframe.mapper.BaseMapper;
import com.hq.cloudplatform.baseframe.mapper.SubjectMapper;
import com.hq.cloudplatform.baseframe.service.ISubjectService;
import com.hq.cloudplatform.baseframe.sys.Constants;
import com.hq.cloudplatform.baseframe.utils.validation.annotation.ValidationMethod;
import org.activiti.engine.TaskService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service("subjectService")
public class SubjectServiceImpl extends BaseServiceImpl<Subject> implements ISubjectService {

    @Autowired
    @Qualifier("subjectMapper")
    private SubjectMapper subjectMapper;

    @Override
    public BaseMapper<Subject> getBaseMapper() {
        return subjectMapper;
    }

    @Override
    @Cacheable(value = Constants.Caches.CFG_CACHE_NAME,
            key = "'dic_' + #parentCode + '_' + #code")
    /*@RequiresRoles("user")*/
    @RequiresPermissions("subject")
    public String getValue(String parentCode, String code) {
        System.out.println("getValue, 说明未使用到缓存");
        return "test";
    }

    @Override
    @ValidationMethod
    public String save(Subject entity) throws ServiceException {
        String id = super.save(entity);

        System.out.println("Subject id:" + id);
        ((ISubjectService)AopContext.currentProxy()).getValue("aaa","bbb");

        if (entity.getName().equals("test")) {
            throw new ServiceException("test");
        }

        return id;
    }
}
