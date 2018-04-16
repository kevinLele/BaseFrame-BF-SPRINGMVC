package com.hq.cloudplatform.baseframe.restful.impl;

import com.hq.cloudplatform.baseframe.entity.BaseEntity;
import com.hq.cloudplatform.baseframe.exception.CheckException;
import com.hq.cloudplatform.baseframe.restful.IBaseRestService;
import com.hq.cloudplatform.baseframe.restful.view.BatchModifyEntity;
import com.hq.cloudplatform.baseframe.restful.view.Page;
import com.hq.cloudplatform.baseframe.restful.view.ResultBean;
import com.hq.cloudplatform.baseframe.service.IBaseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

/**
 * @author admin
 * @date 2017/3/7
 */
@Slf4j
public abstract class BaseRestServiceImpl<Entity extends BaseEntity> implements IBaseRestService<Entity> {

    @Autowired
    @Lazy
    protected HttpServletResponse response;

    @Autowired
    @Lazy
    protected HttpServletRequest request;

    /**
     * 获取service
     *
     * @return
     */
    public abstract IBaseService<Entity> getService();

    /**
     * 检查是否存在
     * 通过传入的参数传换为实体并作为条件进行查询，如果查找到则返回true,否则返回false
     *
     * @param mapBean
     * @return
     */
    @Override
    public ResultBean<Boolean> isExist(@RequestBody Map<String, Object> mapBean) {
        boolean flag = false;
        List<Entity> entityList = this.getService().findByMap(mapBean, "findByMap");

        if (entityList != null && entityList.size() > 0) {
            flag = true;
        }

        return ResultBean.successPack(flag);
    }

    /**
     * 分页获取
     *
     * @param page
     * @return
     */
    @Override
    public ResultBean<Page<Entity>> getPage(@RequestBody Page<Entity> page) {
        return getPage(page, "getCount", "findByPage");
    }

    protected ResultBean<Page<Entity>> getPage(Page<Entity> page, String countFunc, String pageFunc) {
        return ResultBean.successPack(this.getService().findByPage(page, countFunc, pageFunc));
    }

    /**
     * 查询该接口下的所有信息
     *
     * @return
     */
    @Override
    public ResultBean<List<Entity>> getAll() {
        return ResultBean.successPack(this.getService().findAll());
    }

    /**
     * 根据不同的条件进行查询
     *
     * @param mapBean
     * @return
     */
    @Override
    public ResultBean<List<Entity>> getByWhere(@RequestBody Map<String, Object> mapBean) {
        return getByWhere(mapBean, "findByMap");
    }

    protected ResultBean<List<Entity>> getByWhere(Map<String, Object> mapBean, String mapperFunc) {
        return ResultBean.successPack(this.getService().findByMap(mapBean, mapperFunc));
    }

    /**
     * 通过传递的id值进行查询相关信息
     *
     * @param id
     * @return
     */
    @Override
    public ResultBean<Entity> getById(@RequestParam("id") String id) {
        if (StringUtils.isNotBlank(id)) {
            return ResultBean.successPack(this.getService().findById(id));
        } else {
            throw new CheckException("Id can't be null!");
        }
    }

    /**
     * 根据名称查询
     * 只返回查询到的第一条记录
     *
     * @param name
     * @return
     */
    @Override
    public ResultBean<Entity> getByName(@RequestParam("name") String name) {
        if (StringUtils.isNotBlank(name)) {
            return ResultBean.successPack(this.getService().findByName(name));
        } else {
            throw new CheckException("Name can't be null!");
        }
    }

    /**
     * 通过传递的id值删除该id下的记录
     * 逻辑删除
     *
     * @param id
     * @return
     */
    @Override
    public ResultBean<Boolean> removeById(@RequestParam("id") String id) {
        return ResultBean.successPack(this.getService().logicDeleteById(id));
    }

    /**
     * 批量删除
     * 请求参数样例：["id1","id2","id3","id4"]
     *
     * @param idList
     * @return
     */
    @Override
    public ResultBean<Boolean> batchRemove(@RequestBody List<String> idList) {
        if (idList == null || idList.size() <= 0) {
            throw new CheckException("id列表为空");
        } else {
            //逻辑删除
            return ResultBean.successPack(this.getService().logicBatchDelete(idList));
        }
    }

    /**
     * 根据id从数据库中删除
     * 物理删除
     *
     * @param id
     * @return
     */
    @Override
    public ResultBean<Boolean> removeFromDbById(@RequestParam("id") String id) {
        //物理删除
        return ResultBean.successPack(this.getService().deleteById(id));
    }

    @Override
    public ResultBean<Boolean> batchRemoveFromDb(@RequestBody List<String> idList) {
        if (idList == null || idList.size() <= 0) {
            throw new CheckException("id列表为空");
        } else {
            //物理删除
            return ResultBean.successPack(this.getService().batchDelete(idList));
        }
    }

    /**
     * 根据传入的条件进行删除
     *
     * @param mapBean
     * @return
     */
    @Override
    public ResultBean<Boolean> removeByWhere(@RequestBody Map<String, Object> mapBean) {
        return ResultBean.successPack(this.getService().deleteByWhere(mapBean));
    }

    /**
     * 实现新增
     *
     * @param entity
     * @return
     */
    @Override
    public ResultBean<String> save(@RequestBody Entity entity) {
        if (entity != null) {
            return ResultBean.successPack(this.getService().save(entity));
        } else {
            throw new CheckException("entity can't be null!");
        }
    }

    /**
     * 修改记录
     *
     * @param entity
     * @return
     */
    @Override
    public ResultBean<Boolean> modify(@RequestBody Entity entity) {
        if (entity != null) {
            return ResultBean.successPack(this.getService().update(entity));
        } else {
            throw new CheckException("entity can't be null!");
        }
    }

    @Override
    public ResultBean<Boolean> batchModify(@RequestBody BatchModifyEntity<Entity> batchModifyEntity) {
        Entity entity = batchModifyEntity.getEntity();
        List<String> idList = batchModifyEntity.getIdList();

        if (entity != null && idList != null) {
            return ResultBean.successPack(this.getService().batchUpdate(entity, idList));
        } else {
            throw new CheckException("entity or idList can't be null!");
        }
    }

    /**
     * 得到当前的对象class
     *
     * @return
     */
    public Class<Entity> getEntityClass() {
        return (Class<Entity>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }
}
