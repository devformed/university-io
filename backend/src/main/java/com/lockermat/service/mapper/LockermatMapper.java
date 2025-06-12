package com.lockermat.service.mapper;

import com.lockermat.model.dto.lockermat.LockermatEntry;
import com.lockermat.model.dto.lockermat.parcel.ParcelSize;
import com.lockermat.model.entity.lockermat.LockermatEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static com.lockermat.util.Collections.mapList;

/**
 * @author Anton Gorokh
 */
@Mapper
public interface LockermatMapper {

	LockermatMapper INSTANCE = Mappers.getMapper(LockermatMapper.class);

	LockermatEntry toEntry(LockermatEntity entity, Set<ParcelSize> sizes);

	default List<LockermatEntry> toEntries(Collection<LockermatEntity> entities, Map<UUID, Set<ParcelSize>> parcelSizesByLockermatIds) {
		return mapList(entities, o -> toEntry(o, parcelSizesByLockermatIds.get(o.getId())));
	}
}
