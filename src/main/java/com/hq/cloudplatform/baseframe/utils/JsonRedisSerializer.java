package com.hq.cloudplatform.baseframe.utils;

import com.hq.cloudplatform.baseframe.utils.json.JacksonUtil;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * @author Administrator
 * @date 7/24/2017
 */
public class JsonRedisSerializer implements RedisSerializer<Object> {

    @Override
    public byte[] serialize(Object o) throws SerializationException {
        return (null == o ? null : JacksonUtil.toJSONString(o).getBytes());
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        return (null == bytes ? null : JacksonUtil.parseMap(new String(bytes)));
    }
}
