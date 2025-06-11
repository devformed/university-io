package com.lockermat.service.command;

import com.lockermat.model.dto.Position;
import com.lockermat.model.dto.lockermat.parcel.reservation.ReservationReserveRequest;
import com.lockermat.model.dto.reservation.ReservationResponse;
import com.lockermat.model.repository.ParcelRepository;
import com.lockermat.service.query.ParcelQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReservationCommandService {

	private final long minWindowBetweenReservationsSeconds;
	private final ParcelRepository parcelRepo;
	private final ParcelQueryService parcelQuery;
	private final ParcelCommandService parcelCommand;

	@Autowired
	public ReservationCommandService(@Value("${com.lockermat.parcel.reservation.min-window-bw-reservations-sec:#{60 * 60 * 24 * 7}}") long minWindowBetweenReservationsSeconds,
									 ParcelRepository parcelRepo, ParcelQueryService parcelQuery, ParcelCommandService parcelCommand) {
		this.minWindowBetweenReservationsSeconds = minWindowBetweenReservationsSeconds;
		this.parcelRepo = parcelRepo;
		this.parcelQuery = parcelQuery;
		this.parcelCommand = parcelCommand;
	}

	@Transactional
	public Long reserve(ReservationReserveRequest request) {
		// find any parcel within the lockermat of given size that is available for reservation (which means that the window between planned and existing reservations is at least minWindowBetweenReservationsSeconds)
		// then return reservationId
		return null;
	}

	public void cancel(Long reservationId) {
		// mark the given reservation as cancelled
	}

	public void openRemotely(Position position, Long reservationId) {
		// same as parcelCommand.openRemotely
	}
}