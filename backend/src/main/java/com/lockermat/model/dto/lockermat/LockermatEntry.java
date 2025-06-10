package com.lockermat.model.dto.lockermat;

import com.lockermat.model.dto.Position;

import java.util.Set;

/**
 * @author Anton Gorokh
 */
public record LockermatEntry(
		Long id,
		String address,
		Set<ParcelSize> availableSizes,
		Position position
) {
}
