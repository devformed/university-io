package com.lockermat.model.dto.lockermat;

import com.lockermat.model.dto.Position;
import com.lockermat.model.dto.lockermat.parcel.CellSize;

import java.util.Set;

/**
 * @author Anton Gorokh
 */
public record LockermatEntry(
		Long id,
		String address,
		Position position,
		Set<CellSize> sizes
) {
}
