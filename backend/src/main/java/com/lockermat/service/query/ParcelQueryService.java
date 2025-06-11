package com.lockermat.service.query;

import com.lockermat.model.dto.lockermat.parcel.ParcelSize;
import com.lockermat.model.entity.lockermat.ParcelEntity;
import com.lockermat.model.repository.lockermat.ParcelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static com.lockermat.util.Utils.map;

/**
 * @author Anton Gorokh
 */
@Service
@Transactional(readOnly = true)
public class ParcelQueryService {

	private final long minWindowBetweenReservationsSeconds;
	private final ParcelRepository parcelRepository;

	@Autowired
	public ParcelQueryService(@Value("${com.lockermat.parcel.reservation.min-window-bw-reservations-sec:#{60 * 60 * 24 * 7}}") long minWindowBetweenReservationsSeconds, ParcelRepository parcelRepository) {
		this.minWindowBetweenReservationsSeconds = minWindowBetweenReservationsSeconds;
		this.parcelRepository = parcelRepository;
	}

	public Optional<ParcelEntity> findAnyAvailable(UUID lockermatId, ParcelSize size, Instant reservationFrom, Instant reservationTo) {
		return parcelRepository.findAnyAvailable(lockermatId, map(size, Enum::name), reservationFrom, reservationTo, minWindowBetweenReservationsSeconds);
	}
}
