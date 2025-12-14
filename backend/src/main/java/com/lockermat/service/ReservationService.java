package com.lockermat.service;

import com.lockermat.model.dto.Position;
import com.lockermat.model.dto.lockermat.parcel.reservation.ReservationEntry;
import com.lockermat.model.dto.lockermat.parcel.reservation.ReservationReserveRequest;
import com.lockermat.model.entity.lockermat.ParcelEntity;
import com.lockermat.model.entity.lockermat.ReservationEntity;
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

	private final ReservationRepository reservationRepo;
	private final ParcelService parcelService;

	public Long reserve(ReservationReserveRequest request) {
		Optional<ParcelEntity> parcel = parcelService.findAnyWithoutActiveReservations(request.lockermatId(), request.size());
		ReservationEntity reservation = new ReservationEntity(null, parcel.orElseThrow(), request.from(), request.to());
		return reservationRepo.save(reservation).getId();
	}

	public void cancel(Long reservationId) {
		var entity = reservationRepo.getReferenceById(reservationId);
		reservationRepo.delete(entity);
	}

	public void openRemotely(Position position, Long reservationId) {
		var reservation = reservationRepo.getReferenceById(reservationId);
		var parcelId = reservation.getParcel().getId();
		parcelService.openRemotely(parcelId, position.latitude(), position.longitude());
	}

	public List<ReservationEntry> findAll() {
		return ReservationMapper.INSTANCE.toEntries(reservationRepo.findById(List.of(1L)));
	}
}