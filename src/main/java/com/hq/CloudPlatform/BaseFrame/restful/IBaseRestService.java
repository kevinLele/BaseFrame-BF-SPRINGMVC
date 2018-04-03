package com.hq.CloudPlatform.BaseFrame.restful;

import com.hq.CloudPlatform.BaseFrame.restful.view.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface IBaseRestService {

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
     * @param jsonStr
     * @return
     */
    @PostMapping(
            value = "getByWhere",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    String getByWhere(String jsonStr);

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
    String getById(@RequestParam("id") String id);

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
    String getByName(@RequestParam("name") String name);

    /**
     * 检查是否存在
     * 通过传入的参数传换为实体并作为条件进行查询，如果查找到则返回true,否则返回false
     *
     * @param jsonStr
     * @return
     */
    @PostMapping(
            value = "isExist",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    String isExist(String jsonStr);

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
    String removeById(@RequestParam("id") String id);

    /**
     * 根据传入的id列表进行批量逻辑删除
     * 逻辑删除,数据库中标识为已删除的状态，表中必须有is_delete字段
     *
     * @param jsonStr
     * @return
     */
    @PostMapping(
            value = "batchRemove",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    String batchRemove(String jsonStr);

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
    String removeFromDbById(@RequestParam("id") String id);

    /**
     * 根据传入的id列表进行批量删除
     * 物理删除
     *
     * @param jsonStr
     * @return
     */
    @PostMapping(
            value = "batchRemoveFromDb",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    String batchRemoveFromDb(String jsonStr);

    /**
     * 根据传入的条件进行删除
     *
     * @param jsonStr
     * @return
     */
    @PostMapping(
            value = "removeByWhere",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    String removeByWhere(String jsonStr);

    /**
     * 保存
     * 将实体bean转化成jsonStr
     *
     * @param jsonStr
     * @return
     */
    @PostMapping(
            value = "save",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    String save(String jsonStr);

    /**
     * 编辑
     * 将实体bean转化成jsonStr
     *
     * @param jsonStr
     * @return
     */
    @PostMapping(
            value = "modify",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    String modify(String jsonStr);

    /**
     * 批量修改
     *
     * @param jsonStr
     * @return
     */
    @PostMapping(
            value = "batchModify",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    String batchModify(String jsonStr);
}
