package com.lockermat.model.repository.lockermat;

import com.lockermat.model.dto.lockermat.LockermatFilter;
import com.lockermat.model.dto.lockermat.parcel.ParcelSize;
import com.lockermat.model.entity.base.AbstractEntity_;
import com.lockermat.model.entity.lockermat.LockermatEntity;
import com.lockermat.model.entity.lockermat.LockermatEntity_;
import com.lockermat.model.entity.lockermat.ParcelEntity;
import com.lockermat.model.entity.lockermat.ParcelEntity_;
import com.lockermat.model.entity.lockermat.ReservationEntity;
import com.lockermat.model.entity.lockermat.ReservationEntity_;
import com.lockermat.model.repository.base.JpaRepository2;
import com.lockermat.model.repository.base.Specs;
import com.lockermat.model.repository.base.postgres.PostgreSqlFunction;
import jakarta.annotation.Nullable;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface LockermatRepository extends JpaRepository2<LockermatEntity>, LockermatCustomRepository {

	default List<LockermatEntity> findAvailable(LockermatFilter filter, @Nullable Pageable pageable) {
		var specs = new ArrayList<Specification<LockermatEntity>>();
		if (filter.fulltext() != null) {
			specs.add(Specs.equalIgnoreCase(LockermatEntity_.address, filter.fulltext()));
		}
		if (filter.position() != null) {
			specs.add(Specs.orderBy((cb, cq, from) ->
					List.of(cb.asc(PostgreSqlFunction.stDistance(cb, from.get(LockermatEntity_.location), filter.position())))));
		}
		specs.add((root, cq, cb) -> hasAvailableReservationSubquery(cb, cq, root, filter.sizes()));
		return pageable == null ? findAll(Specs.and(specs)) : findAll(Specs.and(specs), pageable).getContent();
	}

	private static Predicate hasAvailableReservationSubquery(CriteriaBuilder cb, CriteriaQuery<?> cq,
															 From<?, LockermatEntity> lockermatRoot, @Nullable Set<ParcelSize> sizes) {
		Subquery<Long> subquery = cq.subquery(Long.class);
		Root<ReservationEntity> reservationRoot = subquery.from(ReservationEntity.class);
		Join<ReservationEntity, ParcelEntity> parcelJoin = reservationRoot.join(ReservationEntity_.parcel);

		var sizesPredicate = sizes == null ? cb.conjunction() : parcelJoin.get(ParcelEntity_.size).in(sizes);

		subquery.select(
				reservationRoot.get(ReservationEntity_.id)
		).where(
				cb.equal(parcelJoin.get(ParcelEntity_.lockermat).get(AbstractEntity_.id), lockermatRoot.get(AbstractEntity_.id)),
				sizesPredicate,
				cb.lessThanOrEqualTo(reservationRoot.get(ReservationEntity_.to), Instant.now())
		);
		return cb.exists(subquery);
	}
}