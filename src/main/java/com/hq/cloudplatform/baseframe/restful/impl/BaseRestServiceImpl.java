package com.hq.cloudplatform.baseframe.restful.impl;

import com.alibaba.fastjson.JSON;
import com.hq.cloudplatform.baseframe.entity.BaseEntity;
import com.hq.cloudplatform.baseframe.restful.IBaseRestService;
import com.hq.cloudplatform.baseframe.restful.view.BatchModifyEntity;
import com.hq.cloudplatform.baseframe.restful.view.Page;
import com.hq.cloudplatform.baseframe.restful.view.ResultBean;
import com.hq.cloudplatform.baseframe.service.IBaseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.UnauthorizedException;
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

        try {
            List<Entity> entityList = this.getService().findByMap(mapBean, "findByMap");

            if (entityList != null && entityList.size() > 0) {
                flag = true;
            }

            return ResultBean.successPack(flag);
        } catch (UnauthorizedException unauthorizedException) {
            return ResultBean.unauthorizedPack();
        } catch (Exception e) {
            log.error("BaseRestServiceImpl isExist is error,{jsonStr:" + JSON.toJSONString(mapBean) + "}", e);
            return ResultBean.failPack(JSON.toJSONString(flag));
        }
    }

    /**
     * 分页获取
     * <p>
     * 请求参数样例：
     * {
     * "pageSize": 10,
     * "pageNumber": 1,
     * "conditions": {
     * "jobNum": "No.113"
     * }
     * }
     * <p>
     * 响应样例：
     * {
     * "content": {
     * "conditions": {
     * "jobNum": "No.113"
     * },
     * "pageNumber": 1,
     * "pageSize": 10,
     * "startRowNum": 0,
     * "endRowNum": 10,
     * "total": 1,
     * "totalPageNum": 1,
     * "rows": [
     * {
     * "id": "C5F0CA07EA864022B1BF072DB4161119",
     * "jobNum": "No.113",
     * "loginName": "lisi",
     * "orgId": "5",
     * "password": "e10adc3949ba59abbe56e057f20f883e",
     * "roleList": [],
     * "username": "李四"
     * }
     * ]
     * },
     * "message": "",
     * "status": "success"
     * }
     *
     * @param page
     * @return
     */
    @Override
    public ResultBean<Page<Entity>> getPage(@RequestBody Page<Entity> page) {
        return getPage(page, "getCount", "findByPage");
    }

    protected ResultBean<Page<Entity>> getPage(Page<Entity> page, String countFunc, String pageFunc) {
        try {
            return ResultBean.successPack(this.getService().findByPage(page, countFunc, pageFunc));
        } catch (UnauthorizedException unauthorizedException) {
            return ResultBean.unauthorizedPack();
        } catch (Exception e) {
            log.error("BaseRestServiceImpl getPage is error,{Page:"
                    + JSON.toJSONString(page) + ",countFunc:" + countFunc + ",pageFunc:" + pageFunc + "}", e);
            return ResultBean.failPack(e);
        }
    }

    /**
     * 查询该接口下的所有信息
     *
     * @return
     */
    @Override
    public ResultBean<List<Entity>> getAll() {
        try {
            List<Entity> list = this.getService().findAll();
            return ResultBean.successPack(list);
        } catch (UnauthorizedException unauthorizedException) {
            return ResultBean.unauthorizedPack();
        } catch (Exception e) {
            log.error("BaseRestServiceImpl getAll is error", e);
            return ResultBean.failPack(e);
        }
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
        try {
            List<Entity> list = this.getService().findByMap(mapBean, mapperFunc);

            if (list.size() != 0) {
                return ResultBean.successPack(list);
            } else {
                return ResultBean.failPack("fail");
            }

        } catch (UnauthorizedException unauthorizedException) {
            return ResultBean.unauthorizedPack();
        } catch (Exception e) {
            log.error("BaseRestServiceImpl getByWhere is error，{jsonStr:"
                    + JSON.toJSONString(mapBean) + ",mapperFunc:" + mapperFunc + "}", e);
            return ResultBean.failPack(e);
        }
    }

    /**
     * 通过传递的id值进行查询相关信息
     *
     * @param id
     * @return
     */
    @Override
    public ResultBean<Entity> getById(@RequestParam("id") String id) {
        try {
            if (StringUtils.isNotBlank(id)) {
                Entity entity = this.getService().findById(id);
                return ResultBean.successPack(entity);
            } else {
                return ResultBean.failPackMessage("undefined", "Id can't be null!");
            }
        } catch (UnauthorizedException unauthorizedException) {
            return ResultBean.unauthorizedPack();
        } catch (Exception e) {
            log.error("BaseRestServiceImpl getById is error,{id:" + id + "}", e);
            return ResultBean.failPack(e);
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
        ResultBean<Entity> resultBean = new ResultBean<>();

        try {
            if (StringUtils.isNotBlank(name)) {
                Entity entity = this.getService().findByName(name);
                return ResultBean.successPack(entity);
            } else {
                return ResultBean.failPackMessage("undefined", "Name can't be null!");
            }
        } catch (UnauthorizedException unauthorizedException) {
            return ResultBean.unauthorizedPack();
        } catch (Exception e) {
            log.error("BaseRestServiceImpl getByName is error,{id:" + name + "}", e);
            return ResultBean.failPack(e);
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
        boolean flag = false;
        ResultBean<Boolean> resultBean = new ResultBean<>();

        try {
            //逻辑删除
            return ResultBean.successPack(this.getService().logicDeleteById(id));
        } catch (UnauthorizedException unauthorizedException) {
            return ResultBean.unauthorizedPack();
        } catch (Exception e) {
            log.error("BaseRestServiceImpl removeById is error,{Id:" + id + "}", e);
            return ResultBean.failPack(JSON.toJSONString(flag));
        }
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
        try {
            if (idList.size() <= 0) {
                return ResultBean.successPack(false, "id列表为空！");
            } else {
                //逻辑删除
                return ResultBean.successPack(this.getService().logicBatchDelete(idList));
            }
        } catch (UnauthorizedException unauthorizedException) {
            return ResultBean.unauthorizedPack();
        } catch (Exception e) {
            log.error("BaseRestServiceImpl batchRemove is error," + JSON.toJSONString(idList), e);
            return ResultBean.failPack(e);
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
        try {
            //物理删除
            return ResultBean.successPack(this.getService().deleteById(id));
        } catch (UnauthorizedException unauthorizedException) {
            return ResultBean.unauthorizedPack();
        } catch (Exception e) {
            log.error("BaseRestServiceImpl removeFromDbById is error,{Id:" + id + "}", e);
            return ResultBean.failPack(e);
        }
    }

    @Override
    public ResultBean<Boolean> batchRemoveFromDb(@RequestBody List<String> idList) {
        try {
            if (idList.size() <= 0) {
                return ResultBean.successPack(false);
            } else {
                //物理删除
                return ResultBean.successPack(this.getService().batchDelete(idList));
            }
        } catch (UnauthorizedException unauthorizedException) {
            return ResultBean.unauthorizedPack();
        } catch (Exception e) {
            log.error("BaseRestServiceImpl batchRemoveFromDb is error," + JSON.toJSONString(idList), e);
            return ResultBean.failPack(e);
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
        try {
            return ResultBean.successPack(this.getService().deleteByWhere(mapBean));
        } catch (UnauthorizedException unauthorizedException) {
            return ResultBean.unauthorizedPack();
        } catch (Exception e) {
            log.error("BaseRestServiceImpl removeByWhere is error，{jsonStr:" + JSON.toJSONString(mapBean) + "}", e);
            return ResultBean.failPack(e);
        }
    }

    /**
     * 实现新增
     *
     * @param entity
     * @return
     */
    @Override
    public ResultBean<String> save(@RequestBody Entity entity) {
        try {
            if (entity != null) {
                String id = this.getService().save(entity);

                if ("exists".equals(id)) {
                    return ResultBean.failPack("exists");
                } else {
                    return ResultBean.successPack(id);
                }
            } else {
                return ResultBean.failPack("entity can't be null!");
            }
        } catch (UnauthorizedException unauthorizedException) {
            return ResultBean.unauthorizedPack();
        } catch (Exception e) {
            String message = e.getMessage();

            if (StringUtils.isBlank(message)) {
                message = "保存数据失败！";
            }

            log.error("BaseRestServiceImpl save is error,{jsonStr:" + JSON.toJSONString(entity) + "}," + e.getMessage(), e);
            return ResultBean.failPackMessage("false", message);
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
        ResultBean<Boolean> resultBean = new ResultBean<>();

        try {
            if (entity != null) {
                return ResultBean.successPack(this.getService().update(entity));
            } else {
                return ResultBean.failPack("entity can't be null!");
            }
        } catch (UnauthorizedException unauthorizedException) {
            return ResultBean.unauthorizedPack();
        } catch (Exception e) {
            String message = e.getMessage();

            if (StringUtils.isBlank(message)) {
                message = "更新数据失败！";
            }

            log.error("BaseRestServiceImpl modify is error,{jsonStr:" + JSON.toJSONString(entity) + "}," + e.getMessage(), e);
            return ResultBean.failPackMessage(false, message);
        }
    }

    @Override
    public ResultBean<Boolean> batchModify(@RequestBody BatchModifyEntity<Entity> batchModifyEntity) {
        try {
            Entity entity = batchModifyEntity.getEntity();
            List<String> idList = batchModifyEntity.getIdList();

            if (entity != null && idList != null) {
                return ResultBean.successPack(this.getService().batchUpdate(entity, idList));
            } else {
                return ResultBean.failPack("entity or idList can't be null!");
            }
        } catch (UnauthorizedException unauthorizedException) {
            return ResultBean.unauthorizedPack();
        } catch (Exception e) {
            String message = e.getMessage();

            if (StringUtils.isBlank(message)) {
                message = "批量更新数据失败！";
            }

            log.error("BaseRestServiceImpl batchModify is error,{batchModifyEntity:" + JSON.toJSONString(batchModifyEntity) + "}," + e.getMessage(), e);
            return ResultBean.failPackMessage(false, message);
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
