package com.lockermat.model.dto.lockermat;

import com.lockermat.model.dto.Position;
import com.lockermat.model.dto.lockermat.parcel.CellSize;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;

import java.util.Set;

/**
 * @author Anton Gorokh
 */
public record LockermatFilter(
		@Nullable String fulltext,
		@Nullable Set<CellSize> sizes,
		@Nullable @Valid Position position
) {
}
