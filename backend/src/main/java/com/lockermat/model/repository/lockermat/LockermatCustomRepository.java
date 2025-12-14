package com.lockermat.model.repository.lockermat;

import com.lockermat.model.dto.lockermat.parcel.ParcelSize;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @author Anton Gorokh
 */
public interface LockermatCustomRepository {

	Map<Long, Set<ParcelSize>> findParcelSizesByLockermatIds(Collection<Long> lockermatIds);
}
