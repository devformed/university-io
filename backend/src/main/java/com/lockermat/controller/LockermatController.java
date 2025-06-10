package com.lockermat.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.lockermat.model.dto.Page;
import com.lockermat.model.dto.lockermat.LockermatEntry;
import com.lockermat.model.dto.lockermat.LockermatFilter;
import com.lockermat.util.Utils;
import jakarta.annotation.Nullable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Anton Gorokh
 */
@RestController
@RequestMapping(path = "/lockermats", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class LockermatController {

	@PostMapping
	public Page<LockermatEntry> find(@RequestBody(required = false) @Nullable Page<LockermatFilter> filters) {
		// TODO
		return Utils.fromJsonSneaky("""
				[
					{
						"id": 1,
						"address": "123 Main St, Springfield",
						"longitude": 12.345678,
						"latitude": 98.765432,
						"availableSizes": ["S", "M"]
					},
					{
						"id": 2,
						"address": "456 Elm St, Springfield",
						"longitude": 23.456789,
						"latitude": 87.654321,
						"availableSizes": ["M", "L"]
					}
				]
				""", new TypeReference<>() {
		});
	}
}
