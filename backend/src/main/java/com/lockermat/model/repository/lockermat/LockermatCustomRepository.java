package com.lockermat.model.repository.lockermat;

import com.lockermat.model.dto.lockermat.parcel.ParcelSize;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * @author Anton Gorokh
 */
public interface LockermatCustomRepository {

	Map<UUID, Set<ParcelSize>> findParcelSizesByLockermatIds(Collection<UUID> lockermatIds);
}
