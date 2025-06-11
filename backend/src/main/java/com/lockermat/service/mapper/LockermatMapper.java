package com.lockermat.service.mapper;

import com.lockermat.model.dto.lockermat.LockermatEntry;
import com.lockermat.model.entity.lockermat.LockermatEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;

import static com.lockermat.util.Collections.mapList;

/**
 * @author Anton Gorokh
 */
@Mapper
public interface LockermatMapper {

	LockermatMapper INSTANCE = Mappers.getMapper(LockermatMapper.class);

	LockermatEntry toEntry(LockermatEntity entity);

	default List<LockermatEntry> toEntries(Collection<LockermatEntity> entities) {
		return mapList(entities, this::toEntry);
	}
}
