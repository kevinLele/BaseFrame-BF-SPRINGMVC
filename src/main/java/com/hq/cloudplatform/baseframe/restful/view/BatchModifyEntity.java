package com.hq.cloudplatform.baseframe.restful.view;

import java.util.List;

/**
 * 用于进行批量修改的实体Bean
 *
 * @param <Entity>
 */
public class BatchModifyEntity<Entity> {

    /**
     * 用于保存需要修改为指定值的实体Bean
     */
    private Entity entity;

    /**
     * 要进行修改操作的实体ID列表
     */
    private List<String> idList;

    public List<String> getIdList() {
        return idList;
    }

    public void setIdList(List<String> idList) {
        this.idList = idList;
    }

    public Entity getEntity() {

        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }
}
