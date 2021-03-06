package com.hq.cloudplatform.baseframe.utils.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hq.cloudplatform.baseframe.exception.JsonParseException;
import com.hq.cloudplatform.baseframe.restful.view.User;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * json字符与对象间的转换工具
 *
 * @author Administrator
 */
@Slf4j
public class JacksonUtil {

    private static ObjectMapper objectMapper;

    public static ObjectMapper getObjectMapper() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();

            //允许单引号
            objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

            //忽略空属性
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        }

        return objectMapper;
    }

    /**
     * 使用泛型方法，把json字符串转换为相应的JavaBean对象。
     * (1)转换为普通JavaBean：readValue(json,Student.class)
     * (2)转换为List,如List<Student>,将第二个参数传递为Student
     * [].class.然后使用Arrays.asList();方法把得到的数组转换为特定类型的List
     *
     * @param jsonStr
     * @param valueType
     * @return
     */
    public static <T> T parseObject(String jsonStr, Class<T> valueType) {
        try {
            return getObjectMapper().readValue(jsonStr, valueType);
        } catch (IOException e) {
            throw new JsonParseException(e);
        }
    }

    /**
     * json数组转List
     *
     * @param jsonStr
     * @param valueType
     * @param <T>
     * @return
     */
    public static <T> List<T> parseList(String jsonStr, Class<T> valueType) {
        ObjectMapper objectMapper = getObjectMapper();
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, valueType);

        try {
            return (List<T>) objectMapper.readValue(jsonStr, javaType);
        } catch (IOException e) {
            throw new JsonParseException(e);
        }
    }

    public static Map<String, Object> parseMap(String jsonStr) {
        try {
            return getObjectMapper().readValue(jsonStr, Map.class);
        } catch (IOException e) {
            throw new JsonParseException(e);
        }
    }

    public static <T> Map<String, T> parseMap(String jsonStr, Class<T> valueType) {
        ObjectMapper objectMapper = getObjectMapper();
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(Map.class, String.class, valueType);

        try {
            return objectMapper.readValue(jsonStr, javaType);
        } catch (IOException e) {
            throw new JsonParseException(e);
        }
    }

    /**
     * 把JavaBean转换为json字符串
     *
     * @param object
     * @return
     */
    public static String toJSONString(Object object) {
        try {
            return getObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new JsonParseException(e);
        }
    }

    public static void main(String[] args) {
        String jsonString = "[{\"id\":\"1\"},{\"id\":\"2\"}]";
        List<User> beanList = parseList(jsonString, User.class);

        for (User user : beanList) {
            System.out.println(user.getId());
        }

        jsonString = "{\"user1\":{\"id\":\"1\"},\"user2\":{\"id\":\"2\"},\"user3\":{\"id\":\"111\"}}";
        Map<String, User> map = parseMap(jsonString, User.class);

        System.out.println(toJSONString(map));
    }
}
