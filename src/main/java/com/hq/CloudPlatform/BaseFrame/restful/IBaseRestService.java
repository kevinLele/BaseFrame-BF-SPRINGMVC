package com.hq.CloudPlatform.BaseFrame.restful;

import com.alibaba.fastjson.JSONObject;
import com.hq.CloudPlatform.BaseFrame.restful.view.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

public interface IBaseRestService<Entity> {

    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    @PostMapping(
            value = "getPage",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    String getPage(Page page);

    /**
     * 获取所有数据
     *
     * @return
     */
    @GetMapping(
            value = "getAll",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    String getAll();

    /**
     * 根据条件查询
     * 将条件实体bean转化成jsonStr
     *
     * @param mapBean
     * @return
     */
    @PostMapping(
            value = "getByWhere",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    String getByWhere(Map<String, Object> mapBean);

    /**
     * 根据id查询
     * 只返回查询到的第一条记录
     *
     * @param id
     * @return
     */
    @GetMapping(
            value = "getById",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    String getById(String id);

    /**
     * 根据名称查询
     * 只返回查询到的第一条记录
     *
     * @param name
     * @return
     */
    @GetMapping(
            value = "getByName",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    String getByName(String name);

    /**
     * 检查是否存在
     * 通过传入的参数传换为实体并作为条件进行查询，如果查找到则返回true,否则返回false
     *
     * @param mapBean
     * @return
     */
    @PostMapping(
            value = "isExist",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    String isExist(Map<String, Object> mapBean);

    /**
     * 根据id进行逻辑删除
     * 逻辑删除,数据库中标识为已删除的状态，表中必须有is_delete字段
     *
     * @param id
     * @return
     */
    @GetMapping(
            value = "removeById",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    String removeById(String id);

    /**
     * 根据传入的id列表进行批量逻辑删除
     * 逻辑删除,数据库中标识为已删除的状态，表中必须有is_delete字段
     *
     * @param idList
     * @return
     */
    @PostMapping(
            value = "batchRemove",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    String batchRemove(List<String> idList);

    /**
     * 根据id从数据库中删除
     * 物理删除
     *
     * @param id
     * @return
     */
    @GetMapping(
            value = "removeFromDbById",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    String removeFromDbById(String id);

    /**
     * 根据传入的id列表进行批量删除
     * 物理删除
     *
     * @param idList
     * @return
     */
    @PostMapping(
            value = "batchRemoveFromDb",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    String batchRemoveFromDb(List<String> idList);

    /**
     * 根据传入的条件进行删除
     *
     * @param mapBean
     * @return
     */
    @PostMapping(
            value = "removeByWhere",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    String removeByWhere(Map<String, Object> mapBean);

    /**
     * 保存
     * 将实体bean转化成jsonStr
     *
     * @param entity
     * @return
     */
    @PostMapping(
            value = "save",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    String save(Entity entity);

    /**
     * 编辑
     * 将实体bean转化成jsonStr
     *
     * @param entity
     * @return
     */
    @PostMapping(
            value = "modify",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    String modify(Entity entity);

    /**
     * 批量修改
     *
     * @param jsonObject
     * @return
     */
    @PostMapping(
            value = "batchModify",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    String batchModify(JSONObject jsonObject);
}
