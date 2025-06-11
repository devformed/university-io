package com.lockermat.model.dto.lockermat.parcel.reservation;

import com.lockermat.model.dto.lockermat.parcel.ParcelSize;

import java.time.Instant;
import java.util.UUID;

/**
 * @author Anton Gorokh
 */
public record ReservationReserveRequest(
		UUID lockermatId,
		ParcelSize size,
		Instant from,
		Instant to
) {
}
