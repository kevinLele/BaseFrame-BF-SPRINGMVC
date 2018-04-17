package com.hq.cloudplatform.baseframe.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BaseDAO<Entity> {

    void save(Entity entity);

    void update(Entity entity);

    void batchUpdate(Map<String, Object> map);

    void deleteByWhere(Map<String, Object> map);

    void logicDeleteByWhere(Map<String, Object> map);

    void deleteById(@Param("id") String id);

    void logicDeleteById(@Param("id") String id);

    void batchDelete(@Param("idList") List<String> idList);

    void logicBatchDelete(@Param("idList") List<String> idList);

    Entity findById(@Param("id") String id);

    Entity findByName(@Param("name") String name);

    List<Entity> findAll();

    List<Entity> findByMap(Map<String, Object> map);

    Integer getCount(Map<String, Object> map);

    List<Entity> findByPage(Map<String, Object> map);
}
