package com.capstone.producer.shared;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.RuntimeJsonMappingException;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class JsonHelper {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception exception) {
            throw new RuntimeJsonMappingException("Failed to serialize object to json");
        }
    }

    public static <T> Optional<T> fromJson(String json, Class<T> clazz) {
        try {
            var value = objectMapper.readValue(json, clazz);
            return Optional.of(value);
        } catch (Exception exception) {
            log.error("Error deserializing object", exception);
            return Optional.empty();
        }
    }
}


