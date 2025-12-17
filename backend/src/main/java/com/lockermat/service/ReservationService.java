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

import java.util.List;
import java.util.Optional;

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
		Optional<LockermatCellEntity> parcel = parcelRepository.findAnyWithoutActiveReservations(request.lockermatId(), request.size());
		ReservationEntity reservation = new ReservationEntity(null, parcel.orElseThrow(), request.from(), request.to());
		return reservationRepository.save(reservation).getId();
	}

	public void cancel(Long reservationId) {
		var entity = reservationRepository.getReferenceById(reservationId);
		reservationRepository.delete(entity);
	}

	public List<ReservationEntry> findAll() {
		return ReservationMapper.INSTANCE.toEntries(reservationRepository.findAll());
	}
}