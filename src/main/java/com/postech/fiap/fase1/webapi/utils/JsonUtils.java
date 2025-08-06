package com.postech.fiap.fase1.webapi.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.postech.fiap.fase1.webapi.infrastructure.exception.ApplicationException;
import lombok.experimental.UtilityClass;

@UtilityClass
public class JsonUtils {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String objectToJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new ApplicationException("Error generating json.");
        }
    }

    public <T> T jsonToObject(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new ApplicationException("Error parsing json.");
        }
    }
}
