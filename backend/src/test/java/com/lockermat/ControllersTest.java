package com.lockermat;

import com.fasterxml.jackson.core.type.TypeReference;
import com.lockermat.model.dto.Page;
import com.lockermat.model.dto.lockermat.LockermatFilter;
import com.lockermat.model.dto.lockermat.parcel.CellSize;
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

import java.io.File;
import java.nio.file.Files;
import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@Sql(scripts = "classpath:test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class ControllersTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private LockermatRepository lockermatRepository;

	@Test
	void openapi() throws Exception {
		String response = mvc.perform(get("/v3/api-docs.yaml"))
				.andReturn()
				.getResponse()
				.getContentAsString();

		String baseDir = ControllersTest.class.getClassLoader().getResource("").getPath();
		File destinationFile = new File(baseDir + "/api.yml");
		destinationFile.getParentFile().mkdirs();
		destinationFile.createNewFile();
		Files.writeString(destinationFile.toPath(), response);
	}

	@Test
	void lockermatsController_findAll() throws Exception {
		Page<LockermatFilter> req = new Page<>(0, 10, new LockermatFilter(null, null, null));
		mvc.perform(get("/lockermats")
						.contentType(MediaType.APPLICATION_JSON)
						.content(Json.toJson(req)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.size()", equalTo(2)));

		mvc.perform(get("/lockermats/page")
						.contentType(MediaType.APPLICATION_JSON)
						.content(Json.toJson(req)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.size()", equalTo(2)));
	}

	@Test
	void reservationFlow_reserve_find_cancel() throws Exception {
		LockermatEntity lockermat = lockermatRepository.getOne(Specs.equal(LockermatEntity_.address, "A Street"));

		// reserve
		ReservationReserveRequest req = new ReservationReserveRequest(
				lockermat.getId(), CellSize.S,
				Instant.parse("2023-01-03T00:00:00Z"),
				Instant.parse("2023-01-04T00:00:00Z")
		);
		Long newId = Long.valueOf(mvc.perform(put("/lockermats/reservations/reserve")
						.contentType(MediaType.APPLICATION_JSON).content(Json.toJson(req)))
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString());

		// find all
		List<ReservationEntry> list = Json.fromJson(mvc.perform(get("/lockermats/reservations")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString(), new TypeReference<>() {
		});
		assertThat(list).extracting(ReservationEntry::id).contains(newId);

		// cancel
		mvc.perform(put("/lockermats/reservations/cancel")
						.param("reservationId", newId.toString()))
				.andExpect(status().isOk());

		List<ReservationEntry> afterList = Json.fromJson(mvc.perform(get("/lockermats/reservations").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString(), new TypeReference<>() {
		});
		assertThat(afterList).doesNotContain(list.stream()
				.filter(e -> e.id().equals(newId)).findFirst().orElse(null));
	}
}