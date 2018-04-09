package com.hq.cloudplatform.baseframe.service;


import com.hq.cloudplatform.baseframe.entity.Subject;

public interface ISubjectService extends IBaseService<Subject> {

    String getValue(String parentCode, String code);
}
