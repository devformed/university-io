package com.lockermat.model.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

/**
 * @author Anton Gorokh
 */
public record Position(
		@NotNull @DecimalMin(value = "-90.0") @DecimalMax(value = "90.0")
		BigDecimal latitude,
		@NotNull @DecimalMin(value = "-180.0") @DecimalMax(value = "180.0")
		BigDecimal longitude
) {
}
