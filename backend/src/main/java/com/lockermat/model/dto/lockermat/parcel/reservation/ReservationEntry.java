package com.lockermat.model.dto.lockermat.parcel.reservation;

import java.time.Instant;

public record ReservationEntry(
    Long id,
    Long parcelId,
    Instant from,
    Instant to
) {
}