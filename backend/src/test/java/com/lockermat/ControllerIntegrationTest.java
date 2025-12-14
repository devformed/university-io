package com.lockermat;

import com.fasterxml.jackson.core.type.TypeReference;
import com.lockermat.model.dto.Page;
import com.lockermat.model.dto.lockermat.LockermatFilter;
import com.lockermat.model.dto.lockermat.parcel.ParcelSize;
import com.lockermat.model.dto.lockermat.parcel.reservation.ReservationEntry;
import com.lockermat.model.dto.lockermat.parcel.reservation.ReservationReserveRequest;
import com.lockermat.model.entity.lockermat.LockermatEntity;
import com.lockermat.model.entity.lockermat.LockermatEntity_;
import com.lockermat.model.repository.base.Specs;
import com.lockermat.model.repository.lockermat.LockermatRepository;
import com.lockermat.util.Json;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@Sql(scripts = "classpath:test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class ControllerIntegrationTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private LockermatRepository lockermatRepository;

	@Test
	void lockermatsController_findAll() throws Exception {
		Page<LockermatFilter> req = new Page<>(0, 10, new LockermatFilter(null, null, null));
		mvc.perform(post("/lockermats")
						.contentType(MediaType.APPLICATION_JSON)
						.content(Json.toJson(req)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data", hasSize(2)));
	}

	@Test
	void reservationFlow_reserve_find_cancel() throws Exception {
		LockermatEntity lockermat = lockermatRepository.getOne(Specs.equal(LockermatEntity_.address, "A Street"));

		ReservationReserveRequest req = new ReservationReserveRequest(
				lockermat.getId(), ParcelSize.S,
				Instant.parse("2023-01-03T00:00:00Z"),
				Instant.parse("2023-01-04T00:00:00Z")
		);
		String res = mvc.perform(put("/lockermats/parcels/reservations/reserve")
						.contentType(MediaType.APPLICATION_JSON).content(Json.toJson(req)))
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString();

		Long newId = Long.valueOf(res);

		// now findAll
		String all = mvc.perform(get("/lockermats/parcels/reservations").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		List<ReservationEntry> list = Json.fromJson(all, new TypeReference<>() {
		});
		assertThat(list).extracting(ReservationEntry::id).contains(newId);

		// cancel
		mvc.perform(put("/lockermats/parcels/reservations/cancel")
						.param("reservationId", newId.toString()))
				.andExpect(status().isOk());
		String after = mvc.perform(get("/lockermats/parcels/reservations").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		List<ReservationEntry> afterList = Json.fromJson(after, new TypeReference<>() {
		});
		assertThat(afterList).doesNotContain(list.stream()
				.filter(e -> e.id().equals(newId)).findFirst().orElse(null));
	}
}