package com.lockermat.model.repository.lockermat;

import com.lockermat.model.dto.lockermat.parcel.ParcelSize;
import com.lockermat.model.entity.base.AbstractEntity_;
import com.lockermat.model.entity.lockermat.ParcelEntity;
import com.lockermat.model.entity.lockermat.ParcelEntity_;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Anton Gorokh
 */
@Repository
@RequiredArgsConstructor
public class LockermatCustomRepositoryImpl implements LockermatCustomRepository {

	private final EntityManager em;

	@Override
	public Map<Long, Set<ParcelSize>> findParcelSizesByLockermatIds(Collection<Long> lockermatIds) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Tuple> query = cb.createTupleQuery();
		Root<ParcelEntity> parcelRoot = query.from(ParcelEntity.class);

		query.multiselect(
				parcelRoot.get(ParcelEntity_.lockermat).get(AbstractEntity_.id), parcelRoot.get(ParcelEntity_.size)
		).where(
				parcelRoot.get(ParcelEntity_.lockermat).get(AbstractEntity_.id).in(lockermatIds)
		).distinct(true);

		return em.createQuery(query)
				.getResultStream()
				.collect(Collectors.groupingBy(tuple -> tuple.get(0, Long.class), Collectors.mapping(tuple -> tuple.get(1, ParcelSize.class), Collectors.toSet())));
	}
}
