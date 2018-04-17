package com.hq.cloudplatform.baseframe.restful;

import com.hq.cloudplatform.baseframe.restful.view.BatchModifyEntity;
import com.hq.cloudplatform.baseframe.restful.view.Page;
import com.hq.cloudplatform.baseframe.restful.view.ResultBean;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
public interface IBaseRestService<Entity> {

    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    @PostMapping(
            value = "/getPage",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "分页查询")
    ResultBean<Page<Entity>> getPage(Page<Entity> page);

    /**
     * 获取所有数据
     *
     * @return
     */
    @GetMapping(
            value = "/getAll",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "查询所有")
    ResultBean<List<Entity>> getAll();

    /**
     * 根据条件查询
     * 将条件实体bean转化成jsonStr
     *
     * @param mapBean
     * @return
     */
    @PostMapping(
            value = "/getByWhere",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "根据传入的参数进行查询")
    ResultBean<List<Entity>> getByWhere(Map<String, Object> mapBean);

    /**
     * 根据传入的对象进行查询
     *
     * @param entity
     * @return
     */
    @PostMapping(
            value = "/getByExample",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "根据传入的对象进行查询")
    ResultBean<List<Entity>> getByExample(Entity entity) throws Exception;


    /**
     * 根据id查询
     * 只返回查询到的第一条记录
     *
     * @param id
     * @return
     */
    @GetMapping(
            value = "/getById",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "根据实体ID进行查询")
    ResultBean<Entity> getById(String id);

    /**
     * 根据名称查询
     * 只返回查询到的第一条记录
     *
     * @param name
     * @return
     */
    @GetMapping(
            value = "/getByName",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "根据名称进行查询", notes = "如果记实体没有name字段请忽略该接口")
    ResultBean<Entity> getByName(String name);

    /**
     * 检查是否存在
     * 通过传入的参数传换为实体并作为条件进行查询，如果查找到则返回true,否则返回false
     *
     * @param mapBean
     * @return
     */
    @PostMapping(
            value = "/isExist",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "检查实体是否存在")
    ResultBean<Boolean> isExist(Map<String, Object> mapBean);

    /**
     * 根据id进行逻辑删除
     * 逻辑删除,数据库中标识为已删除的状态，表中必须有is_delete字段
     *
     * @param id
     * @return
     */
    @GetMapping(
            value = "/removeById",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "根据实体ID进行删除操作", notes = "该操作只进行逻辑删除，如需进行物理删除请使用removeFromDbById接口")
    ResultBean<Boolean> removeById(String id);

    /**
     * 根据传入的id列表进行批量逻辑删除
     * 逻辑删除,数据库中标识为已删除的状态，表中必须有is_delete字段
     *
     * @param idList
     * @return
     */
    @PostMapping(
            value = "/batchRemove",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "根据实体ID列表进行批量删除操作", notes = "该操作只进行逻辑删除，如需进行物理删除请使用batchRemoveFromDb接口")
    ResultBean<Boolean> batchRemove(List<String> idList);

    /**
     * 根据id从数据库中删除
     * 物理删除
     *
     * @param id
     * @return
     */
    @GetMapping(
            value = "/removeFromDbById",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "根据实体ID进行删除操作", notes = "该操作将进行物理删除，如需进行逻辑删除请使用removeById接口")
    ResultBean<Boolean> removeFromDbById(String id);

    /**
     * 根据传入的id列表进行批量删除
     * 物理删除
     *
     * @param idList
     * @return
     */
    @PostMapping(
            value = "/batchRemoveFromDb",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "根据实体ID列表进行批量删除操作", notes = "该操作将进行物理删除，如需进行物理删除请使用batchRemove接口")
    ResultBean<Boolean> batchRemoveFromDb(List<String> idList);

    /**
     * 根据传入的条件进行删除
     *
     * @param mapBean
     * @return
     */
    @PostMapping(
            value = "/removeByWhereFromDb",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "根据传入的参数进行删除操作", notes = "该操作将进行物理删除，如需进行逻辑删除请使用removeById接口")
    ResultBean<Boolean> removeByWhereFromDb(Map<String, Object> mapBean);

    /**
     * 根据传入的条件进行删除
     *
     * @param mapBean
     * @return
     */
    @PostMapping(
            value = "/removeByWhere",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "根据传入的参数进行删除操作", notes = "该操作将进行物理删除，如需进行逻辑删除请使用removeById接口")
    ResultBean<Boolean> removeByWhere(Map<String, Object> mapBean);

    /**
     * 保存
     * 将实体bean转化成jsonStr
     *
     * @param entity
     * @return
     */
    @PostMapping(
            value = "/save",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "保存实体信息", notes = "即将实体信息进行入库")
    ResultBean<String> save(Entity entity);

    /**
     * 编辑
     * 将实体bean转化成jsonStr
     *
     * @param entity
     * @return
     */
    @PostMapping(
            value = "/modify",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "修改实体信息")
    ResultBean<Boolean> modify(Entity entity);

    /**
     * 批量修改
     *
     * @param batchModifyEntity
     * @return
     */
    @PostMapping(
            value = "/batchModify",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "批量修改实体信息")
    ResultBean<Boolean> batchModify(BatchModifyEntity<Entity> batchModifyEntity);
}
