package com.lockermat.controller.lockermat;

import com.lockermat.model.dto.Page;
import com.lockermat.model.dto.lockermat.LockermatEntry;
import com.lockermat.model.dto.lockermat.LockermatFilter;
import com.lockermat.service.LockermatService;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/lockermats", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class LockermatsController {

	private final LockermatService service;

	@Schema(description = "Find all available locations (unpaginated)")
	@PostMapping
	public List<LockermatEntry> getAvailableLocations(@Validated @RequestBody LockermatFilter request) {
		return service.getAvailableLocations(request);
	}

	@Schema(description = "Find all available locations (paginated)")
	@PostMapping(path = "/page")
	public Page<List<LockermatEntry>> getAvailableLocationsPage(@Validated @RequestBody Page<LockermatFilter> request) {
		return service.getAvailableLocationsPage(request);
	}
}