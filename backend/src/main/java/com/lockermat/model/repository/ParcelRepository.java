package com.lockermat.model.repository;

import com.lockermat.model.dto.lockermat.parcel.ParcelSize;
import com.lockermat.model.entity.ParcelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ParcelRepository extends JpaRepository<ParcelEntity, Long> {
    List<ParcelEntity> findByLockermat_Id(Long lockermatId);
    List<ParcelEntity> findByLockermat_IdAndSize(Long lockermatId, ParcelSize size);
    @Query(value = "SELECT ST_DWithin(l.location::geography, ST_SetSRID(ST_MakePoint(:lon, :lat),4326)::geography, :meters) " +
                   "FROM parcel p JOIN lockermat l ON p.lockermat_id = l.id " +
                   "WHERE p.id = :parcelId", nativeQuery = true)
    boolean isWithinDistance(@Param("parcelId") Long parcelId,
                             @Param("lon") double lon,
                             @Param("lat") double lat,
                             @Param("meters") double meters);
}