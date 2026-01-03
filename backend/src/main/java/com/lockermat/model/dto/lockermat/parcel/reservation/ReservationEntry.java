package com.lockermat.model.dto.lockermat.parcel.reservation;

import java.time.Instant;

public record ReservationEntry(
    Long id,
	Long lockermatId,
    Long cellId,
    Instant from,
    Instant to
) {
}