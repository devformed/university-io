package com.lockermat.model.dto.lockermat;

import com.lockermat.model.dto.Position;
import com.lockermat.model.dto.lockermat.parcel.ParcelSize;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.Set;

/**
 * @author Anton Gorokh
 */
public record LockermatFilter(
		@Nullable String fulltext,
		@Nullable Set<ParcelSize> sizes,
		@Nullable @Valid Position position
) {
}
