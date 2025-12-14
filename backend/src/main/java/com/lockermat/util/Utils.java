package com.lockermat.util;

import jakarta.annotation.Nullable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.lockermat.util.Optionull.optionull;

/**
 * @author Anton Gorokh
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Utils {

	// syntax sugar methods

	public static <T> T nn(T value) {
		return Objects.requireNonNull(value);
	}

	public static <T> T nn(T value, T orElse) {
		return Objects.requireNonNullElse(value, orElse);
	}

	public static <T> T nn(T value, Supplier<? extends T> orElseGet) {
		return Objects.requireNonNullElseGet(value, orElseGet);
	}

	public static <T, R> R opt(@Nullable T value, Function<T, R> mapping) {
		return optionull(value).opt(mapping);
	}

	public static <T extends Exception> T thenThrow(Supplier<T> exception) throws T {
		throw exception.get();
	}
}

