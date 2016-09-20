package com.aixforce.session.redis;

import com.aixforce.session.SerializeException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2013-05-21
 */
public class JsonSerializer {
    private final static Logger log = LoggerFactory.getLogger(JsonSerializer.class);
    private final ObjectMapper objectMapper;
    private final JavaType javaType;

    public JsonSerializer() {
        objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        javaType = objectMapper.getTypeFactory().constructParametricType(Map.class, String.class, Object.class);
    }

    public String serialize(Object o) {
        try {
            return objectMapper.writeValueAsString(o);
        } catch (Exception e) {
            log.error("failed to serialize http session {} to json,cause:{}",o, Throwables.getStackTraceAsString(e));
            throw new SerializeException("failed to serialize http session to json",e);
        }
    }

    public Map<String,Object> deserialize(String o) {
        try {
            return objectMapper.readValue(o, javaType);
        } catch (Exception e) {
            log.error("failed to deserialize string  {} to http session,cause:{} ",o,Throwables.getStackTraceAsString(e));
            throw new SerializeException("failed to deserialize string to http session",e);
        }
    }
}
