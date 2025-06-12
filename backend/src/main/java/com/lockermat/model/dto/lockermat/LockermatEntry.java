package com.lockermat.model.dto.lockermat;

import com.lockermat.model.dto.Position;
import com.lockermat.model.dto.lockermat.parcel.ParcelSize;

import java.util.Set;
import java.util.UUID;

/**
 * @author Anton Gorokh
 */
public record LockermatEntry(
		UUID id,
		String address,
		Position position,
		Set<ParcelSize> sizes
) {
}
