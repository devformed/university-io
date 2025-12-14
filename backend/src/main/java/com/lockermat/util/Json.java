package com.lockermat.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

/**
 * @author Anton Gorokh
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Json {

    private final static ObjectMapper MAPPER = new ObjectMapper();

    @SneakyThrows
    public static String toJson(Object value) {
        return MAPPER.writeValueAsString(value);
    }

    @SneakyThrows
    public static <T> T fromJson(String json, TypeReference<T> type) {
        return MAPPER.readValue(json, type);
    }
}
