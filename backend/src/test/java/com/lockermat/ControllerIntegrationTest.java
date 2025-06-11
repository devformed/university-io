package com.lockermat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lockermat.model.dto.Page;
import com.lockermat.model.dto.Position;
import com.lockermat.model.dto.lockermat.LockermatFilter;
import com.lockermat.model.dto.lockermat.parcel.reservation.ReservationReserveRequest;
import com.lockermat.model.dto.lockermat.parcel.reservation.ReservationEntry;
import com.lockermat.model.dto.lockermat.parcel.ParcelSize;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.PostgisContainerProvider;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@Sql(scripts = "classpath:test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class ControllerIntegrationTest {


    @Autowired
    private MockMvc mvc;
    @Autowired
    private  ObjectMapper mapper;

    @Test
    void findLockermats_emptyFilter_returnsAll() throws Exception {
        Page<LockermatFilter> req = new Page<>(0, 10, new LockermatFilter(null,null,null,null,null));
        String json = mapper.writeValueAsString(req);
        mvc.perform(post("/lockermats")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.data", hasSize(2)));
    }

    @Test
    void openParcelRemotely_succeeds_whenClose() throws Exception {
        UUID parcelId = UUID.fromString("10000000-0000-0000-0000-000000000001");
        Position pos = new Position(BigDecimal.valueOf(20.0), BigDecimal.valueOf(10.0));
        mvc.perform(put("/lockermats/parcels/open-remotely")
                .param("parcelId", parcelId.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(pos)))
            .andExpect(status().isOk());
    }

    @Test
    void reservationFlow_reserve_find_cancel() throws Exception {
        UUID lockermatId = UUID.fromString("00000000-0000-0000-0000-000000000001");
        ReservationReserveRequest req = new ReservationReserveRequest(
            lockermatId, ParcelSize.S,
            Instant.parse("2023-01-03T00:00:00Z"),
            Instant.parse("2023-01-04T00:00:00Z")
        );
        String body = mapper.writeValueAsString(req);
        String res = mvc.perform(put("/lockermats/parcels/reservations/reserve")
                .contentType(MediaType.APPLICATION_JSON).content(body))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();
        UUID newId = mapper.readValue(res, UUID.class);
        // now findAll
        String all = mvc.perform(get("/lockermats/parcels/reservations").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();
        List<ReservationEntry> list = mapper.readValue(all,
            mapper.getTypeFactory().constructCollectionType(List.class, ReservationEntry.class));
        assertThat(list).extracting(ReservationEntry::id).contains(newId);
        // cancel
        mvc.perform(put("/lockermats/parcels/reservations/cancel")
                .param("reservationId", newId.toString()))
            .andExpect(status().isOk());
        String after = mvc.perform(get("/lockermats/parcels/reservations").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();
        List<ReservationEntry> afterList = mapper.readValue(after,
            mapper.getTypeFactory().constructCollectionType(List.class, ReservationEntry.class));
        assertThat(afterList).doesNotContain(list.stream()
            .filter(e->e.id().equals(newId)).findFirst().orElse(null));
    }
}