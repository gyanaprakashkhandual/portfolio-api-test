package com.framework.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class JsonUtils {

    private static final Logger log        = LogManager.getLogger(JsonUtils.class);
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String TEST_DATA   = "src/test/resources/testdata/";

    private JsonUtils() {}

    public static <T> T fromFile(String relativePath, Class<T> type) {
        File file = new File(TEST_DATA + relativePath);
        try {
            return mapper.readValue(file, type);
        } catch (IOException e) {
            log.error("Failed to deserialize file: {}", relativePath, e);
            throw new RuntimeException("JSON read error: " + relativePath, e);
        }
    }

    public static Map<String, Object> fileToMap(String relativePath) {
        File file = new File(TEST_DATA + relativePath);
        try {
            return mapper.readValue(file, new TypeReference<Map<String, Object>>() {});
        } catch (IOException e) {
            log.error("Failed to parse file to map: {}", relativePath, e);
            throw new RuntimeException("JSON parse error: " + relativePath, e);
        }
    }

    public static String toJson(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (IOException e) {
            log.error("Failed to serialize object: {}", object, e);
            throw new RuntimeException("JSON write error", e);
        }
    }

    public static <T> T fromJson(String json, Class<T> type) {
        try {
            return mapper.readValue(json, type);
        } catch (IOException e) {
            log.error("Failed to deserialize JSON string", e);
            throw new RuntimeException("JSON parse error", e);
        }
    }
}