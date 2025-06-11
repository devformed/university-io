package com.lockermat.service.mapper;

import com.lockermat.model.dto.lockermat.parcel.reservation.ReservationEntry;
import com.lockermat.model.entity.lockermat.ReservationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;

import static com.lockermat.util.Collections.mapList;

/**
 * @author Anton Gorokh
 */
@Mapper
public interface ReservationMapper {

	ReservationMapper INSTANCE = Mappers.getMapper(ReservationMapper.class);

	@Mapping(target = "parcelId", source = "parcel.id")
	ReservationEntry toEntry(ReservationEntity entity);

	default List<ReservationEntry> toEntries(Collection<ReservationEntity> entities) {
		return mapList(entities, this::toEntry);
	}
}
