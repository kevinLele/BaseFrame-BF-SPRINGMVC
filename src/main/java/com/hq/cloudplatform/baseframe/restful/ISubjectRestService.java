package com.hq.cloudplatform.baseframe.restful;

import com.hq.cloudplatform.baseframe.restful.view.ResultBean;
import com.hq.cloudplatform.baseframe.restful.view.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @author Administrator
 */
@RequestMapping("/api/subject")
public interface ISubjectRestService<Entity> extends IBaseRestService<Entity> {

    @PostMapping(
            value = "getByWhereWithRelationShip",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResultBean getByWhereWithRelationShip(@RequestBody Map<String, Object> mapBean);

    @PostMapping(
            value = "getPageWithRelationShip",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResultBean getPageWithRelationShip(@RequestBody Page page);

    @GetMapping("testcache")
    String getValue();
}