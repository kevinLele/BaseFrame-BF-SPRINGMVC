package com.hq.cloudplatform.baseframe.service;

import com.hq.cloudplatform.baseframe.restful.view.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/3/7.
 */
public interface IBaseService<Entity> {

    String generateUUID();

    Page findByPage(Page<Entity> page, String countMapperFunc, String pageMapperFunc);

    String save(Entity entity);

    boolean update(Entity entity);

    boolean batchUpdate(Entity entity, List<String> idList);

    boolean deleteById(String id);

    boolean deleteByWhere(Map<String, Object> map);

    boolean logicDeleteByWhere(Map<String, Object> map);

    boolean logicDeleteById(String id);

    boolean batchDelete(List<String> idList);

    boolean logicBatchDelete(List<String> idList);

    Entity findById(String id);

    Entity findByName(String name);

    List<Entity> findAll();

    List<Entity> findByMap(Map<String, Object> map, String mapperFunc);
}
