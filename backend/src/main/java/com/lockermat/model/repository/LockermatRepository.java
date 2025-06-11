package com.lockermat.model.repository;

import com.lockermat.model.entity.LockermatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface LockermatRepository extends JpaRepository<LockermatEntity, Long> {
    @Query(value = "SELECT l.id AS id, l.address AS address, ST_X(l.location) AS lon, ST_Y(l.location) AS lat " +
                   "FROM lockermat l " +
                   "ORDER BY CASE WHEN :lon IS NOT NULL AND :lat IS NOT NULL " +
                   "THEN ST_Distance(l.location::geography, ST_SetSRID(ST_MakePoint(:lon, :lat),4326)::geography) ELSE 0 END ASC " +
                   "OFFSET :offset LIMIT :limit", nativeQuery = true)
    List<LockermatProjection> findWithDistanceOrder(@Param("lon") Double lon,
                                                   @Param("lat") Double lat,
                                                   @Param("offset") int offset,
                                                   @Param("limit") int limit);

    interface LockermatProjection {
        Long getId();
        String getAddress();
        Double getLon();
        Double getLat();
    }

    /**
     * Retrieves lockermats including the list of available parcel sizes, filtering by reservation window and optional sizes.
     */
    @Query(value = """
        SELECT
          l.id AS id,
          l.address AS address,
          ST_X(l.location) AS lon,
          ST_Y(l.location) AS lat,
          COALESCE(
            array_agg(DISTINCT p.size) FILTER (
              WHERE NOT EXISTS (
                SELECT 1 FROM reservation r
                WHERE r.parcel_id = p.id
                  AND (:availableFrom IS NULL OR r.reservation_to <= :availableFrom)
                  AND (:availableTo   IS NULL OR r.reservation_from >= :availableTo)
              )
              AND (:sizes IS NULL OR p.size = ANY(:sizes))
            ),
            ARRAY[]::varchar[]
          ) AS sizes
        FROM lockermat l
        JOIN parcel p ON p.lockermat_id = l.id
        GROUP BY l.id, l.address, l.location
        ORDER BY CASE WHEN :lon IS NOT NULL
                  THEN ST_Distance(
                         l.location::geography,
                         ST_SetSRID(ST_MakePoint(:lon, :lat),4326)::geography
                       )
                  ELSE 0 END
        OFFSET :offset LIMIT :limit
        """, nativeQuery = true)
    List<LockermatAvailableProjection> findAvailable(
        @Param("lon") Double lon,
        @Param("lat") Double lat,
        @Param("offset") int offset,
        @Param("limit") int limit,
        @Param("availableFrom") java.time.Instant availableFrom,
        @Param("availableTo")   java.time.Instant availableTo,
        @Param("sizes")         java.util.List<String> sizes
    );

    interface LockermatAvailableProjection extends LockermatProjection {
        java.util.List<String> getSizes();
    }
}