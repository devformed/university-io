package com.lockermat.controller;

import com.lockermat.model.dto.Position;
import com.lockermat.model.dto.lockermat.ParcelSize;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Anton Gorokh
 */
@RestController
@RequestMapping(path = "/lockermats/parcels/reservations", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class LockermatParcelReservationController {

	@PutMapping(path = "/reserve")
	public Long reserve(@RequestParam Long lockermatId, @RequestParam ParcelSize size) {
		// TODO implement this shi
		return 7L;
	}

	@PutMapping(path = "/cancel")
	public void cancel(@RequestParam Long parcelId) {
		// TODO implement this shi
	}

	@PutMapping(path = "/open-remotely")
	public void openRemotely(@RequestBody Position position, @RequestParam Long parcelId) {
		// TODO implement this shi
	}
}
