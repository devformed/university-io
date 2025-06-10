package com.lockermat.model.dto;

/**
 * @author Anton Gorokh
 */
public record Page<T>(int pageNumber, int pageSize, T data) {
}
