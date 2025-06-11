package com.lockermat.controller.lockermat;

import com.lockermat.model.dto.Position;
import com.lockermat.model.dto.lockermat.parcel.reservation.ReservationReserveRequest;
import com.lockermat.model.dto.reservation.ReservationResponse;
import com.lockermat.service.command.ReservationCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @author Anton Gorokh
 */
@RestController
@RequestMapping(path = "/lockermats/parcels/reservations", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class LockermatParcelReservationController {

	private final ReservationCommandService reservationService;

	@PutMapping(path = "/reserve")
	public Long reserve(@RequestBody ReservationReserveRequest request) {
		return reservationService.reserve(request);
	}

	@PutMapping(path = "/cancel")
	public void cancel(@RequestParam Long reservationId) {
		reservationService.cancel(reservationId);
	}

	@PutMapping(path = "/open-remotely", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void openRemotely(@RequestBody Position position, @RequestParam Long reservationId) {
		reservationService.openRemotely(reservationId, position);
	}
}
