package com.lockermat.service;

import com.lockermat.model.dto.lockermat.parcel.ParcelSize;
import com.lockermat.model.entity.lockermat.ParcelEntity;
import com.lockermat.model.repository.lockermat.ParcelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;


/**
 * @author Anton Gorokh
 */
@Service
@Transactional
@RequiredArgsConstructor
public class ParcelService {

	private final ParcelRepository parcelRepository;

	public Optional<ParcelEntity> findAnyWithoutActiveReservations(Long lockermatId, ParcelSize size) {
		return parcelRepository.findAnyWithoutActiveReservations(lockermatId, size);
	}

	public void openRemotely(Long parcelId, BigDecimal latitude, BigDecimal longitude) {
		// todo
	}
}
