package com.lockermat.model.repository.lockermat;

import com.lockermat.model.dto.lockermat.parcel.CellSize;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @author Anton Gorokh
 */
public interface LockermatCustomRepository {

	Map<Long, Set<CellSize>> findParcelSizesByLockermatIds(Collection<Long> lockermatIds);
}
