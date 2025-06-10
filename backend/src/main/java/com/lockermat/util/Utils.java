package com.lockermat.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

/**
 * @author Anton Gorokh
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Utils {

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	public static <T> T fromJson(String json, TypeReference<T> ref) throws JsonProcessingException {
		return OBJECT_MAPPER.readValue(json, ref);
	}

	@SneakyThrows
	public static <T> T fromJsonSneaky(String json, TypeReference<T> ref) {
		return OBJECT_MAPPER.readValue(json, ref);
	}

	public static <T> T nn(T value) {
		if (value != null) return value;
		throw new NullPointerException();
	}
}

