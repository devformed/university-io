package com.lockermat.model.repository.lockermat;

import com.lockermat.model.dto.lockermat.parcel.ParcelSize;
import com.lockermat.model.entity.lockermat.LockermatEntity;
import com.lockermat.model.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Set;

public interface LockermatRepository extends BaseRepository<LockermatEntity> {
//
//	@Query(value = """
//			SELECT
//			  l.id_      AS id,
//			  l.address_ AS address,
//			  ST_X(l.location_) AS lon,
//			  ST_Y(l.location_) AS lat
//			FROM lockermat_ l
//			ORDER BY CASE
//			  WHEN :lng IS NOT NULL AND :lat IS NOT NULL THEN
//			    ST_Distance(
//			      l.location_::geography,
//			      ST_SetSRID(ST_MakePoint(:lon, :lat), 4326)::geography
//			    )
//			  ELSE 0 END ASC
//			OFFSET :offset LIMIT :limit
//			""", nativeQuery = true)
//	List<LockermatEntity> findWithDistanceOrder(@Param("lng") Double lng, @Param("lat") Double lat, @Param("offset") int offset, @Param("limit") int limit);

	@Query(value = QUERY_FIND_AVAILABLE, nativeQuery = true)
	List<LockermatEntity> findAvailable(@Param("lng") Double lng, @Param("lat") Double lat, @Param("availableFrom") Instant availableFrom, @Param("availableTo") Instant availableTo, @Param("sizes") Set<String> sizesIn, @Param("offset") int offset, @Param("limit") int limit);

	String QUERY_FIND_AVAILABLE = """
			SELECT
			  l.id_      AS id,
			  l.address_ AS address,
			  ST_X(l.location_) AS lon,
			  ST_Y(l.location_) AS lat,
			  COALESCE(
			    array_agg(DISTINCT p.size_) FILTER (
			      WHERE NOT EXISTS (
			        SELECT 1 FROM reservation_ r
			        WHERE r.parcel_id_ = p.id_
			          AND (:availableFrom IS NULL OR r.reservation_to_ <= :availableFrom)
			          AND (:availableTo   IS NULL OR r.reservation_from_ >= :availableTo)
			      )
			      AND (:sizes IS NULL OR p.size_ = ANY(:sizes))
			    ),
			    ARRAY[]::varchar[]
			  ) AS sizes
			FROM lockermat_ l
			JOIN parcel_ p ON p.lockermat_id_ = l.id_
			GROUP BY l.id_, l.address_, l.location_
			ORDER BY CASE
			  WHEN :lon IS NOT NULL THEN
			    ST_Distance(
			      l.location_::geography,
			      ST_SetSRID(ST_MakePoint(:lon, :lat), 4326)::geography
			    )
			  ELSE 0 END
			OFFSET :offset LIMIT :limit
			""";
}