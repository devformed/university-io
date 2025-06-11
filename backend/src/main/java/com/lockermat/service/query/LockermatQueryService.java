package com.lockermat.service.query;

import com.lockermat.model.dto.Page;
import com.lockermat.model.dto.Position;
import com.lockermat.model.dto.lockermat.LockermatEntry;
import com.lockermat.model.dto.lockermat.LockermatFilter;
import com.lockermat.model.dto.lockermat.parcel.ParcelSize;
import com.lockermat.model.entity.lockermat.LockermatEntity;
import com.lockermat.model.repository.lockermat.LockermatRepository;
import com.lockermat.service.mapper.LockermatMapper;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static com.lockermat.util.Collections.mapSet;
import static com.lockermat.util.Utils.map;
import static com.lockermat.util.Utils.nn;

/**
 * @author Anton Gorokh
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LockermatQueryService {

	private final LockermatRepository lockermatRepo;

    public Page<List<LockermatEntry>> find(Page<LockermatFilter> request) {
        LockermatFilter filter = map(request, Page::data);

        int pageNum = nn(map(request, Page::pageNumber), 0);
        int pageSize = nn(map(request, Page::pageSize), 20);

        int offset = pageNum * pageSize;
        int limit = pageSize;

        Double lng = map(filter.position(), Position::longitude, BigDecimal::doubleValue);
        Double lat = map(filter.position(), Position::latitude, BigDecimal::doubleValue);
        Set<String> sizes = mapSet(filter.sizes(), ParcelSize::name);

        List<LockermatEntity> lockermats = lockermatRepo.findAvailable(lng, lat, filter.availableFrom(), filter.availableTo(), sizes, offset, limit);
        return new Page<>(pageNum, pageSize, LockermatMapper.INSTANCE.toEntries(lockermats));
    }
}