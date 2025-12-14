package com.lockermat.model.repository.lockermat;

import com.lockermat.model.dto.lockermat.parcel.ParcelSize;
import com.lockermat.model.entity.base.AbstractEntity_;
import com.lockermat.model.entity.lockermat.ParcelEntity;
import com.lockermat.model.entity.lockermat.ParcelEntity_;
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

public interface ParcelRepository extends JpaRepository2<ParcelEntity> {

	default Optional<ParcelEntity> findAnyWithoutActiveReservations(Long lockermatId, ParcelSize size) {
		return findAny(
				Specs.equal(ParcelEntity_.lockermat, lockermatId),
				Specs.equal(ParcelEntity_.size, size),
				(cb, cq, root) -> noActiveReservation(root, cq, cb)
		);
	};

	private static Predicate noActiveReservation(CriteriaBuilder builder, AbstractQuery<?> query, From<?, ParcelEntity> parcelFrom) {
		Subquery<Long> subquery = query.subquery(Long.class);
		Root<ReservationEntity> reservationRoot = subquery.from(ReservationEntity.class);

		subquery.select(
				reservationRoot.get(AbstractEntity_.id)
		).where(
				builder.equal(reservationRoot.get(ReservationEntity_.parcel).get(AbstractEntity_.id), parcelFrom.get(AbstractEntity_.id)),
				builder.lessThanOrEqualTo(reservationRoot.get(ReservationEntity_.from), Instant.now())
		);
		return builder.exists(subquery);
	}
}