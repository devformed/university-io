package com.lockermat.model.repository.lockermat;

import com.lockermat.model.dto.lockermat.parcel.CellSize;
import com.lockermat.model.entity.base.AbstractEntity_;
import com.lockermat.model.entity.lockermat.LockermatCellEntity;
import com.lockermat.model.entity.lockermat.LockermatCellEntity_;
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
	public Map<Long, Set<CellSize>> findParcelSizesByLockermatIds(Collection<Long> lockermatIds) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Tuple> query = cb.createTupleQuery();
		Root<LockermatCellEntity> parcelRoot = query.from(LockermatCellEntity.class);

		query.select(
				cb.tuple(parcelRoot.get(LockermatCellEntity_.lockermat).get(AbstractEntity_.id), parcelRoot.get(LockermatCellEntity_.size))
		).where(
				parcelRoot.get(LockermatCellEntity_.lockermat).get(AbstractEntity_.id).in(lockermatIds)
		).distinct(true);

		return em.createQuery(query)
				.getResultStream()
				.collect(Collectors.groupingBy(tuple -> tuple.get(0, Long.class), Collectors.mapping(tuple -> tuple.get(1, CellSize.class), Collectors.toSet())));
	}
}
