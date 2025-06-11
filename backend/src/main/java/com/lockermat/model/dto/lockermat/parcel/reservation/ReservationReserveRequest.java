package com.lockermat.model.dto.lockermat.parcel.reservation;

import com.lockermat.model.dto.lockermat.parcel.ParcelSize;

import java.time.Instant;

/**
 * @author Anton Gorokh
 */
public record ReservationReserveRequest(Long lockermatId, ParcelSize size, Instant from, Instant to) {
}
