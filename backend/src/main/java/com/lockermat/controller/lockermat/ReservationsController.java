package com.lockermat.controller.lockermat;

import com.lockermat.model.dto.lockermat.parcel.reservation.ReservationEntry;
import com.lockermat.model.dto.lockermat.parcel.reservation.ReservationReserveRequest;
import com.lockermat.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Anton Gorokh
 */
@RestController
@RequestMapping(path = "/lockermats/reservations", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ReservationsController {

    private final ReservationService reservationService;

	@PutMapping(path = "/reserve")
	public Long reserve(@RequestBody ReservationReserveRequest request) {
		return reservationService.reserve(request);
	}

	@PutMapping(path = "/cancel")
	public void cancel(@RequestParam Long reservationId) {
		reservationService.cancel(reservationId);
	}

    @GetMapping
    public List<ReservationEntry> findAll() {
        return reservationService.findAll();
    }
}
