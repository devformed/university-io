package com.lockermat.controller.lockermat;

import com.lockermat.model.dto.Page;
import com.lockermat.model.dto.lockermat.LockermatEntry;
import com.lockermat.model.dto.lockermat.LockermatFilter;
import com.lockermat.service.query.LockermatQueryService;
import jakarta.annotation.Nullable;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/lockermats", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class LockermatController {

	private final LockermatQueryService queryService;

	@PostMapping
	public Page<List<LockermatEntry>> find(@RequestBody(required = false) @Nullable Page<LockermatFilter> filters) {
		return queryService.find(filters);
	}
}