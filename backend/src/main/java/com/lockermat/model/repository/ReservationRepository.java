package com.lockermat.model.repository;

import com.lockermat.model.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.Instant;
import java.util.List;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {
    @Query("SELECT COUNT(r) > 0 FROM ReservationEntity r WHERE r.parcel.id = :parcelId " +
           "AND r.reservationFrom <= :end AND r.reservationTo >= :start")
    boolean existsConflicting(@Param("parcelId") Long parcelId,
                              @Param("start") Instant start,
                              @Param("end")   Instant end);

    @Query("SELECT COUNT(r) > 0 FROM ReservationEntity r WHERE r.parcel.id = :parcelId " +
           "AND r.reservationTo >= :threshold")
    boolean existsRecent(@Param("parcelId") Long parcelId,
                         @Param("threshold") Instant threshold);

    @Query("SELECT r FROM ReservationEntity r WHERE r.parcel.id = :parcelId " +
           "AND r.reservationFrom <= :now AND r.reservationTo >= :now")
    List<ReservationEntity> findActive(@Param("parcelId") Long parcelId,
                                       @Param("now") Instant now);
}