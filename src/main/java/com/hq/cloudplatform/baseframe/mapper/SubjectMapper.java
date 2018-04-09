package com.hq.cloudplatform.baseframe.mapper;

import com.hq.cloudplatform.baseframe.entity.Subject;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("subjectMapper")
public interface SubjectMapper extends BaseMapper<Subject> {

    List<Subject> findByMapWithRelationShip(Map<String, Object> map);

    Integer getCountWithRelationShip(Map<String, Object> map);

    List<Subject> findByPageWithRelationShip(Map<String, Object> map);
}
