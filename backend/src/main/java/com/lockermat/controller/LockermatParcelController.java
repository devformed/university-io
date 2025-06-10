package com.lockermat.controller;

import com.lockermat.model.dto.Position;
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
public class LockermatParcelController {

	@PutMapping(value = "/open-remotely", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void openRemotely(@RequestBody Position position, @RequestParam Long parcelId) {
		// TODO implement this shi
	}
}
