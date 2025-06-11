package com.lockermat.model.dto;

/**
 * @author Anton Gorokh
 */
public record Page<T>(
		Integer pageNumber,
		Integer pageSize,
		T data
) {
}
