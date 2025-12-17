package com.lockermat.model.repository.lockermat;

import com.lockermat.model.entity.lockermat.ReservationEntity;
import com.lockermat.model.repository.base.JpaRepository2;

import java.util.List;

public interface ReservationRepository extends JpaRepository2<ReservationEntity, Long> {
	List<ReservationEntity> findAll();
}