package com.lockermat.model.dto.lockermat.parcel.reservation;

import java.time.Instant;
import java.util.UUID;

public record ReservationEntry(
    UUID id,
    UUID parcelId,
    Instant from,
    Instant to
) {
}