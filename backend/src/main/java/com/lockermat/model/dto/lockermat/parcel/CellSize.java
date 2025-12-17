package com.lockermat.model.dto.lockermat.parcel;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author Anton Gorokh
 */
@Schema(enumAsRef = true)
public enum CellSize {
	S, M, L
}
