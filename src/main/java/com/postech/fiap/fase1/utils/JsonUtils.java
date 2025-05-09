package com.postech.fiap.fase1.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.postech.fiap.fase1.configuration.exception.ApplicationException;

import java.util.Map;

public class JsonUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private JsonUtils() {
    }

    public static String objectToJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new ApplicationException("Error generating json.");
        }
    }

    public static Map<String, Object> objectToMap(Object object) {
        return objectMapper.convertValue(object, new TypeReference<>() {
        });
    }

    public static <T> T jsonToObject(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new ApplicationException("Error parsing json.");
        }
    }
}
