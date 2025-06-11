package com.lockermat.service.query;

import com.lockermat.model.dto.Page;
import com.lockermat.model.dto.lockermat.LockermatEntry;
import com.lockermat.model.dto.lockermat.LockermatFilter;
import com.lockermat.model.repository.LockermatRepository;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LockermatQueryService {

	private final LockermatRepository lockermatRepo;

	public Page<List<LockermatEntry>> find(@Nullable Page<LockermatFilter> pageFilter) {
		// find all lockermats that match the filter (availableFrom/To based on reservation windows (look ReservationCommandService.reserve))
		return null;
	}
}