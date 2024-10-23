package com.bart.api.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * @Author noobBart
 * @Date 2023/7/16/0016 14:54
 */
public class Json {
    public static final ObjectMapper DEFAULT_INSTANCE;
    public static final ObjectMapper NOT_NULL_INSTANCE;

    public Json() {
    }

    static {
        DEFAULT_INSTANCE = (new ObjectMapper()).configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        NOT_NULL_INSTANCE = (new ObjectMapper()).configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false).setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }
}
