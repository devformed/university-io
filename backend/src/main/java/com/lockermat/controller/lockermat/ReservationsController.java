package com.lockermat.controller.lockermat;

import com.lockermat.model.dto.lockermat.parcel.reservation.ReservationEntry;
import com.lockermat.model.dto.lockermat.parcel.reservation.ReservationReserveRequest;
import com.lockermat.service.ReservationService;
import io.swagger.v3.oas.annotations.media.Schema;
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

	@Schema(description = "Reserve any available cell matching parameters. Returns id of the reservation")
	@PutMapping(path = "/reserve")
	public Long reserve(@RequestBody ReservationReserveRequest request) {
		return reservationService.reserve(request);
	}

	@Schema(description = "Delete reservation")
	@PutMapping(path = "/cancel")
	public void cancel(@RequestParam Long reservationId) {
		reservationService.cancel(reservationId);
	}

	@Schema(description = "Find all reservations sorted by from desc")
    @GetMapping
    public List<ReservationEntry> findAll() {
        return reservationService.findAll();
    }
}
