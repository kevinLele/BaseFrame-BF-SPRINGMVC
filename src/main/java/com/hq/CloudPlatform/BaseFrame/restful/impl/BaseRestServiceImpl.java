package com.hq.CloudPlatform.BaseFrame.restful.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hq.CloudPlatform.BaseFrame.entity.BaseEntity;
import com.hq.CloudPlatform.BaseFrame.restful.IBaseRestService;
import com.hq.CloudPlatform.BaseFrame.restful.view.JsonViewObject;
import com.hq.CloudPlatform.BaseFrame.restful.view.Page;
import com.hq.CloudPlatform.BaseFrame.service.IBaseService;
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
    public JsonViewObject<Boolean> isExist(@RequestBody Map<String, Object> mapBean) {
        boolean flag = false;
        JsonViewObject<Boolean> jsonView = new JsonViewObject<>();

        try {
            List<Entity> entityList = this.getService().findByMap(mapBean, "findByMap");

            if (entityList != null && entityList.size() > 0) {
                flag = true;
            }

            jsonView.successPack(flag);
        } catch (UnauthorizedException unauthorizedException) {
            jsonView.unauthorizedPack();
        } catch (Exception e) {
            jsonView.failPack(JSON.toJSONString(flag));
            log.error("BaseRestServiceImpl isExist is error,{jsonStr:" + JSON.toJSONString(mapBean) + "}", e);
        }

        return jsonView;
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
    public JsonViewObject<Page> getPage(@RequestBody Page page) {
        return getPage(page, "getCount", "findByPage");
    }

    protected JsonViewObject<Page> getPage(Page page, String countFunc, String pageFunc) {
        JsonViewObject<Page> jsonView = new JsonViewObject<>();

        try {
            page = this.getService().findByPage(page, countFunc, pageFunc);
            jsonView.successPack(page);
        } catch (UnauthorizedException unauthorizedException) {
            jsonView.unauthorizedPack();
        } catch (Exception e) {
            jsonView.failPack(e);
            log.error("BaseRestServiceImpl getPage is error,{Page:"
                    + JSON.toJSONString(page) + ",countFunc:" + countFunc + ",pageFunc:" + pageFunc + "}", e);
        }

        return jsonView;
    }

    /**
     * 查询该接口下的所有信息
     *
     * @return
     */
    @Override
    public JsonViewObject<List<Entity>> getAll() {
        JsonViewObject<List<Entity>> jsonView = new JsonViewObject<>();

        try {
            List<Entity> list = this.getService().findAll();
            jsonView.successPack(list);
        } catch (UnauthorizedException unauthorizedException) {
            jsonView.unauthorizedPack();
        } catch (Exception e) {
            jsonView.failPack(e);
            log.error("BaseRestServiceImpl getAll is error", e);
        }

        return jsonView;
    }

    /**
     * 根据不同的条件进行查询
     *
     * @param mapBean
     * @return
     */
    @Override
    public JsonViewObject<List<Entity>> getByWhere(@RequestBody Map<String, Object> mapBean) {
        return getByWhere(mapBean, "findByMap");
    }

    protected JsonViewObject<List<Entity>> getByWhere(Map<String, Object> mapBean, String mapperFunc) {
        JsonViewObject<List<Entity>> jsonView = new JsonViewObject<>();

        try {
            List<Entity> list = this.getService().findByMap(mapBean, mapperFunc);

            if (list.size() != 0) {
                jsonView.successPack(list);
            } else {
                jsonView.failPack("fail");
            }

        } catch (UnauthorizedException unauthorizedException) {
            jsonView.unauthorizedPack();
        } catch (Exception e) {
            jsonView.failPack(e);
            log.error("BaseRestServiceImpl getByWhere is error，{jsonStr:"
                    + JSON.toJSONString(mapBean) + ",mapperFunc:" + mapperFunc + "}", e);
        }

        return jsonView;
    }

    /**
     * 通过传递的id值进行查询相关信息
     *
     * @param id
     * @return
     */
    @Override
    public JsonViewObject<Entity> getById(@RequestParam("id") String id) {
        JsonViewObject<Entity> jsonView = new JsonViewObject<>();

        try {
            if (StringUtils.isNotBlank(id)) {
                Entity entity = this.getService().findById(id);
                jsonView.successPack(entity);
            }
        } catch (UnauthorizedException unauthorizedException) {
            jsonView.unauthorizedPack();
        } catch (Exception e) {
            jsonView.failPack(e);
            log.error("BaseRestServiceImpl getById is error,{id:" + id + "}", e);
        }

        return jsonView;
    }

    /**
     * 根据名称查询
     * 只返回查询到的第一条记录
     *
     * @param name
     * @return
     */
    @Override
    public JsonViewObject<Entity> getByName(@RequestParam("name") String name) {
        JsonViewObject<Entity> jsonView = new JsonViewObject<>();

        try {
            if (StringUtils.isNotBlank(name)) {
                Entity entity = this.getService().findByName(name);
                jsonView.successPack(entity);
            }
        } catch (UnauthorizedException unauthorizedException) {
            jsonView.unauthorizedPack();
        } catch (Exception e) {
            jsonView.failPack(e);
            log.error("BaseRestServiceImpl getByName is error,{id:" + name + "}", e);
        }

        return jsonView;
    }

    /**
     * 通过传递的id值删除该id下的记录
     * 逻辑删除
     *
     * @param id
     * @return
     */
    @Override
    public JsonViewObject<Boolean> removeById(@RequestParam("id") String id) {
        boolean flag = false;
        JsonViewObject<Boolean> jsonView = new JsonViewObject<>();

        try {
            //逻辑删除
            flag = this.getService().logicDeleteById(id);
            jsonView.successPack(flag);
        } catch (UnauthorizedException unauthorizedException) {
            jsonView.unauthorizedPack();
        } catch (Exception e) {
            jsonView.failPack(JSON.toJSONString(flag));
            log.error("BaseRestServiceImpl removeById is error,{Id:" + id + "}", e);
        }

        return jsonView;
    }

    /**
     * 批量删除
     * 请求参数样例：["id1","id2","id3","id4"]
     *
     * @param idList
     * @return
     */
    @Override
    public JsonViewObject<Boolean> batchRemove(@RequestBody List<String> idList) {
        boolean flag = false;
        JsonViewObject<Boolean> jsonView = new JsonViewObject<>();

        try {
            if (idList.size() <= 0) {
                jsonView.successPack(false);
            } else {
                //逻辑删除
                flag = this.getService().logicBatchDelete(idList);
                jsonView.successPack(flag);
            }
        } catch (UnauthorizedException unauthorizedException) {
            jsonView.unauthorizedPack();
        } catch (Exception e) {
            jsonView.failPack(JSON.toJSONString(flag));
            log.error("BaseRestServiceImpl batchRemove is error," + JSON.toJSONString(idList), e);
        }

        return jsonView;
    }

    /**
     * 根据id从数据库中删除
     * 物理删除
     *
     * @param id
     * @return
     */
    @Override
    public JsonViewObject<Boolean> removeFromDbById(@RequestParam("id") String id) {
        boolean flag = false;
        JsonViewObject<Boolean> jsonView = new JsonViewObject<>();

        try {
            //物理删除
            flag = this.getService().deleteById(id);
            jsonView.successPack(flag);
        } catch (UnauthorizedException unauthorizedException) {
            jsonView.unauthorizedPack();
        } catch (Exception e) {
            jsonView.failPack(JSON.toJSONString(flag));
            log.error("BaseRestServiceImpl removeFromDbById is error,{Id:" + id + "}", e);
        }

        return jsonView;
    }

    @Override
    public JsonViewObject<Boolean> batchRemoveFromDb(@RequestBody List<String> idList) {
        boolean flag = false;
        JsonViewObject<Boolean> jsonView = new JsonViewObject<>();

        try {
            if (idList.size() <= 0) {
                jsonView.successPack(false);
            } else {
                //物理删除
                flag = this.getService().batchDelete(idList);
                jsonView.successPack(flag);
            }
        } catch (UnauthorizedException unauthorizedException) {
            jsonView.unauthorizedPack();
        } catch (Exception e) {
            jsonView.failPack(JSON.toJSONString(flag));
            log.error("BaseRestServiceImpl batchRemoveFromDb is error," + JSON.toJSONString(idList), e);
        }

        return jsonView;
    }

    /**
     * 根据传入的条件进行删除
     *
     * @param mapBean
     * @return
     */
    @Override
    public JsonViewObject<Boolean> removeByWhere(@RequestBody Map<String, Object> mapBean) {
        JsonViewObject<Boolean> jsonView = new JsonViewObject<>();
        boolean flag;

        try {
            flag = this.getService().deleteByWhere(mapBean);

            jsonView.successPack(flag);
        } catch (UnauthorizedException unauthorizedException) {
            jsonView.unauthorizedPack();
        } catch (Exception e) {
            jsonView.failPack(e);
            log.error("BaseRestServiceImpl removeByWhere is error，{jsonStr:" + JSON.toJSONString(mapBean) + "}", e);
        }

        return jsonView;
    }

    /**
     * 实现新增
     *
     * @param entity
     * @return
     */
    @Override
    public JsonViewObject<String> save(@RequestBody Entity entity) {
        JsonViewObject<String> jsonView = new JsonViewObject<>();

        try {
            if (entity != null) {
                String id = this.getService().save(entity);

                if ("exists".equals(id)) {
                    jsonView.failPack("exists");
                } else {
                    jsonView.successPack(id);
                }
            }
        } catch (UnauthorizedException unauthorizedException) {
            jsonView.unauthorizedPack();
        } catch (Exception e) {
            String message = e.getMessage();

            if (StringUtils.isBlank(message)) {
                message = "保存数据失败！";
            }

            jsonView.failPack("false", message);
            log.error("BaseRestServiceImpl save is error,{jsonStr:" + JSON.toJSONString(entity) + "}," + e.getMessage(), e);
        }

        return jsonView;
    }

    /**
     * 修改记录
     *
     * @param entity
     * @return
     */
    @Override
    public JsonViewObject<Boolean> modify(@RequestBody Entity entity) {
        JsonViewObject<Boolean> jsonView = new JsonViewObject<>();

        try {
            if (entity != null) {
                jsonView.successPack(this.getService().update(entity));
            }
        } catch (UnauthorizedException unauthorizedException) {
            jsonView.unauthorizedPack();
        } catch (Exception e) {
            String message = e.getMessage();

            if (StringUtils.isBlank(message)) {
                message = "更新数据失败！";
            }

            jsonView.failPack(false, message);
            log.error("BaseRestServiceImpl modify is error,{jsonStr:" + JSON.toJSONString(entity) + "}," + e.getMessage(), e);
        }

        return jsonView;
    }

    @Override
    public JsonViewObject<Boolean> batchModify(@RequestBody JSONObject jsonObject) {
        JsonViewObject<Boolean> jsonView = new JsonViewObject<>();

        try {
            String entityJsonStr = jsonObject.getString("entity");
            String idListJsonStr = jsonObject.getString("idList");
            Entity entity = JSON.parseObject(entityJsonStr, this.getEntityClass());
            List<String> idList = JSON.parseArray(idListJsonStr, String.class);

            if (entity != null && idList != null) {
                jsonView.successPack(this.getService().batchUpdate(entity, idList));
            }
        } catch (UnauthorizedException unauthorizedException) {
            jsonView.unauthorizedPack();
        } catch (Exception e) {
            String message = e.getMessage();

            if (StringUtils.isBlank(message)) {
                message = "批量更新数据失败！";
            }

            jsonView.failPack(false, message);
            log.error("BaseRestServiceImpl batchModify is error,{jsonStr:" + JSON.toJSONString(jsonObject) + "}," + e.getMessage(), e);
        }

        return jsonView;
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
