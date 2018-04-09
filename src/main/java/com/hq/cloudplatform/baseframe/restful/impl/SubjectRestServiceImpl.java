package com.hq.cloudplatform.baseframe.restful.impl;

import com.hq.cloudplatform.baseframe.entity.Subject;
import com.hq.cloudplatform.baseframe.restful.ISubjectRestService;
import com.hq.cloudplatform.baseframe.restful.view.ResultBean;
import com.hq.cloudplatform.baseframe.restful.view.Page;
import com.hq.cloudplatform.baseframe.service.IBaseService;
import com.hq.cloudplatform.baseframe.service.ISubjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author Administrator
 */
@RestController
@Slf4j
public class SubjectRestServiceImpl extends BaseRestServiceImpl<Subject> implements ISubjectRestService<Subject> {

    @Autowired
    @Qualifier("subjectService")
    private ISubjectService subjectService;

    @Override
    public IBaseService getService() {
        return subjectService;
    }

    @Override
    public ResultBean getByWhereWithRelationShip(Map<String, Object> mapBean) {
        return getByWhere(mapBean, "findByMapWithRelationShip");
    }

    @Override
    public ResultBean getPageWithRelationShip(Page page) {
        return getPage(page, "getCountWithRelationShip", "findByPageWithRelationShip");
    }

    @Override
    public String getValue() {
        return subjectService.getValue("ccc","ddd1");
    }
}
