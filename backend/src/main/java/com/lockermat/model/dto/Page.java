package com.lockermat.model.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * @author Anton Gorokh
 */
public record Page<T>(
		@Nullable Integer pageNumber,
		@Nullable Integer pageSize,
		@NotNull T data
) {

	public Pageable toPageable() {
		if (pageNumber == null || pageSize == null) {
			return null;
		}
		return PageRequest.of(Math.max(pageNumber, 0), Math.max(pageSize, 0));
	}
}
