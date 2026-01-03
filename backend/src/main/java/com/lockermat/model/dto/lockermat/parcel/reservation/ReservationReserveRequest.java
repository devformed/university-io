package com.lockermat.model.dto.lockermat.parcel.reservation;

import com.lockermat.model.dto.lockermat.parcel.CellSize;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

/**
 * @author Anton Gorokh
 */
public record ReservationReserveRequest(
		@NotNull Long lockermatId,
		@NotNull CellSize size,
		@NotNull Instant from,
		@NotNull Instant to
) {
}
