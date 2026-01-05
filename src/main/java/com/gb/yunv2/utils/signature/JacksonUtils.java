package com.gb.yunv2.utils.signature;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wangyifei
 */
@Slf4j
public class JacksonUtils {
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    public static <T> T jsonString2Obj(String jsonString, Class<T> clazz) {
        JSON_MAPPER.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        return readValue(jsonString, clazz, JSON_MAPPER);
    }

    public static <T> T readValue(String string, Class<T> clazz, ObjectMapper objectMapper) {
        try {
            objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            return objectMapper.readValue(string, clazz);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public static String obj2JsonString(Object obj) {
        return writeValueAsString(obj, JSON_MAPPER);
    }

    public static String writeValueAsString(Object obj, ObjectMapper objectMapper) {
        String string = null;
        try {
            objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            string = objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return string;
    }
}
