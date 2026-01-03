package com.lockermat.service;

import com.lockermat.model.dto.lockermat.parcel.reservation.ReservationEntry;
import com.lockermat.model.dto.lockermat.parcel.reservation.ReservationReserveRequest;
import com.lockermat.model.entity.lockermat.LockermatCellEntity;
import com.lockermat.model.entity.lockermat.ReservationEntity;
import com.lockermat.model.repository.lockermat.LockermatCellRepository;
import com.lockermat.model.repository.lockermat.ReservationRepository;
import com.lockermat.service.mapper.ReservationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Anton Gorokh
 */
@Service
@Transactional
@RequiredArgsConstructor
public class ReservationService {

	private final ReservationRepository reservationRepository;
	private final LockermatCellRepository parcelRepository;

	public Long reserve(ReservationReserveRequest request) {
		LockermatCellEntity cell = parcelRepository.findAnyWithoutActiveReservations(request.lockermatId(), request.size())
				.orElseThrow(() -> new IllegalStateException("Couldn't find available cell"));

		ReservationEntity reservation = new ReservationEntity(null, cell, request.from(), request.to());
		return reservationRepository.save(reservation).getId();
	}

	public void cancel(Long reservationId) {
		var entity = reservationRepository.getReferenceById(reservationId);
		reservationRepository.delete(entity);
	}

	public List<ReservationEntry> findAll() {
		return reservationRepository.findAll()
				.stream()
				.sorted(Comparator.comparing(ReservationEntity::getFrom).reversed())
				.collect(Collectors.collectingAndThen(Collectors.toList(), ReservationMapper.INSTANCE::toEntries));
	}
}