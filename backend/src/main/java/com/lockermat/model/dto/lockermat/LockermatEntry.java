package com.lockermat.model.dto.lockermat;

import com.lockermat.model.dto.Position;

import java.util.UUID;

/**
 * @author Anton Gorokh
 */
public record LockermatEntry(
		UUID id,
		String address,
		Position position
) {
}
