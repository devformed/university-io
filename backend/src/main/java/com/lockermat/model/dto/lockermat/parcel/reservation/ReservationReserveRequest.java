package com.lockermat.model.dto.lockermat.parcel.reservation;

import com.lockermat.model.dto.lockermat.parcel.CellSize;

import java.time.Instant;

/**
 * @author Anton Gorokh
 */
public record ReservationReserveRequest(
		Long lockermatId,
		CellSize size,
		Instant from,
		Instant to
) {
}
