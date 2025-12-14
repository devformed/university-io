package com.lockermat.controller.lockermat;

import com.lockermat.model.dto.Position;
import com.lockermat.service.ParcelService;
import lombok.RequiredArgsConstructor;
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
@RequestMapping(path = "/lockermats/parcels", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class LockermatParcelController {

	private final ParcelService service;

	@PutMapping(path = "/open-remotely", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void openRemotely(@RequestBody Position position, @RequestParam Long parcelId) {
		service.openRemotely(parcelId, position.latitude(), position.longitude());
	}
}
