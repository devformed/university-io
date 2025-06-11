package com.lockermat.service.command;

import com.lockermat.model.dto.Position;
import com.lockermat.model.dto.lockermat.parcel.reservation.ReservationReserveRequest;
import com.lockermat.model.entity.lockermat.ParcelEntity;
import com.lockermat.model.entity.lockermat.ReservationEntity;
import com.lockermat.model.repository.lockermat.ReservationRepository;
import com.lockermat.service.query.ParcelQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Anton Gorokh
 */
@Service
@Transactional
@RequiredArgsConstructor
public class ReservationCommandService {

	private final ReservationRepository reservationRepo;
	private final ParcelQueryService parcelQuery;
	private final ParcelCommandService parcelCommand;

	public UUID reserve(ReservationReserveRequest request) {
		Optional<ParcelEntity> parcel = parcelQuery.findAnyAvailable(request.lockermatId(), request.size(), request.from(), request.to());
		ReservationEntity reservation = new ReservationEntity(UUID.randomUUID(), parcel.orElseThrow(), request.from(), request.to());
		return reservationRepo.save(reservation).getId();
	}

	public void cancel(UUID reservationId) {
		var entity = reservationRepo.getReferenceById(reservationId);
		reservationRepo.delete(entity);
	}

	public void openRemotely(Position position, UUID reservationId) {
		var reservation = reservationRepo.getReferenceById(reservationId);
		var parcelId = reservation.getParcel().getId();
		parcelCommand.openRemotely(parcelId, position.latitude(), position.longitude());
	}
}