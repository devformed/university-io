package com.lockermat.model.repository.lockermat;

import com.lockermat.model.dto.lockermat.parcel.ParcelSize;
import com.lockermat.model.entity.lockermat.ParcelEntity;
import com.lockermat.model.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface ParcelRepository extends BaseRepository<ParcelEntity> {

	@Query(value = QUERY_IS_WITHIN_DISTANCE, nativeQuery = true)
	boolean isWithinDistance(@Param("parcelId") UUID parcelId, @Param("lng") double lng, @Param("lat") double lat, @Param("meters") double meters);

	@Query(value = QUERY_FIND_ANY_AVAILABLE, nativeQuery = true)
	Optional<ParcelEntity> findAnyAvailable(UUID lockermatId, ParcelSize size, Instant reservationFrom, Instant reservationTo, long minWindowBetweenReservationsSeconds);

	String QUERY_IS_WITHIN_DISTANCE = """
			SELECT ST_DWithin(
			  l.location_::geography,
			  ST_SetSRID(ST_MakePoint(:lng, :lat), 4326)::geography,
			  :meters
			)
			FROM parcel_ p
			JOIN lockermat_ l ON p.lockermat_id_ = l.id_
			WHERE p.id_ = :parcelId
			""";

	String QUERY_FIND_ANY_AVAILABLE = """
			TODO
			""";
}