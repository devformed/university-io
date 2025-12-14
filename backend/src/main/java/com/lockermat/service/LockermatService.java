package com.lockermat.service;

import com.lockermat.model.dto.Page;
import com.lockermat.model.dto.lockermat.LockermatEntry;
import com.lockermat.model.dto.lockermat.LockermatFilter;
import com.lockermat.model.dto.lockermat.parcel.ParcelSize;
import com.lockermat.model.entity.lockermat.LockermatEntity;
import com.lockermat.model.repository.lockermat.LockermatRepository;
import com.lockermat.service.mapper.LockermatMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.lockermat.util.Collections2.ids;

/**
 * @author Anton Gorokh
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LockermatService {

	private final LockermatRepository lockermatRepo;

    public Page<List<LockermatEntry>> getAvailableLocationsPage(Page<LockermatFilter> request) {
        List<LockermatEntity> lockermats = lockermatRepo.findAvailable(request.data(), request.toPageable());
        Map<Long, Set<ParcelSize>> parcelSizesByLockermatIds = lockermatRepo.findParcelSizesByLockermatIds(ids(lockermats));

        return new Page<>(request.pageNumber(), request.pageSize(), LockermatMapper.INSTANCE.toEntries(lockermats, parcelSizesByLockermatIds));
    }

    public List<LockermatEntry> getAvailableLocations(LockermatFilter filter) {
        List<LockermatEntity> lockermats = lockermatRepo.findAvailable(filter, null);
        Map<Long, Set<ParcelSize>> parcelSizesByLockermatIds = lockermatRepo.findParcelSizesByLockermatIds(ids(lockermats));

        return LockermatMapper.INSTANCE.toEntries(lockermats, parcelSizesByLockermatIds);
    }
}