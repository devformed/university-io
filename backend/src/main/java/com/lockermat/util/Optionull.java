package com.lockermat.util;

import jakarta.annotation.Nullable;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.util.function.ThrowingFunction;

import java.util.function.Function;

import static com.lockermat.util.Utils.nn;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class Optionull<T> {

    private final static Optionull<?> EMPTY = new Optionull<>(null);

    private final T value;

    @SuppressWarnings("unchecked")
    public static <T> Optionull<T> optionull(@Nullable T value) {
        return value == null ? (Optionull<T>) EMPTY : new Optionull<>(value);
    }

    public <R> Optionull<R> map(Function<T, R> mapping) {
        return value == null ? optionull(null) : optionull(mapping.apply(value));
    }

    public <R> Optionull<R> mapIgnoreException(ThrowingFunction<T, R> mapping) {
        try {
            return map(mapping);
        } catch (Exception e) {
            return optionull(null);
        }
    }

    public <R> R opt(Function<T, R> mapping) {
        return map(mapping).opt();
    }

    public T opt() {
        return value;
    }

    public T get() {
        return nn(value);
    }
}
