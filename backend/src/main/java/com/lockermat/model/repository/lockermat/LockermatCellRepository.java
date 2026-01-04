package com.lockermat.model.repository.lockermat;

import com.lockermat.model.dto.lockermat.parcel.CellSize;
import com.lockermat.model.entity.base.AbstractEntity_;
import com.lockermat.model.entity.lockermat.LockermatCellEntity;
import com.lockermat.model.entity.lockermat.LockermatCellEntity_;
import com.lockermat.model.entity.lockermat.ReservationEntity;
import com.lockermat.model.entity.lockermat.ReservationEntity_;
import com.lockermat.model.repository.base.JpaRepository2;
import com.lockermat.model.repository.base.Specs;
import jakarta.persistence.criteria.AbstractQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

import java.time.Instant;
import java.util.Optional;

public interface LockermatCellRepository extends JpaRepository2<LockermatCellEntity, Long> {

	default Optional<LockermatCellEntity> findAnyWithoutActiveReservations(Long lockermatId, CellSize size) {
		return findAny(
				Specs.equal(LockermatCellEntity_.lockermat, lockermatId),
				Specs.equal(LockermatCellEntity_.size, size),
				(root, cq, cb) -> noActiveReservation(cb, cq, root)
		);
	};

	private static Predicate noActiveReservation(CriteriaBuilder builder, AbstractQuery<?> query, From<?, LockermatCellEntity> cellFrom) {
		Subquery<Long> subquery = query.subquery(Long.class);
		Root<ReservationEntity> reservationRoot = subquery.from(ReservationEntity.class);

		subquery.select(
				reservationRoot.get(AbstractEntity_.id)
		).where(
				builder.equal(reservationRoot.get(ReservationEntity_.cell).get(AbstractEntity_.id), cellFrom.get(AbstractEntity_.id)),
				builder.greaterThan(reservationRoot.get(ReservationEntity_.to), builder.currentTimestamp().as(Instant.class))
		);
		return builder.not(builder.exists(subquery));
	}
}