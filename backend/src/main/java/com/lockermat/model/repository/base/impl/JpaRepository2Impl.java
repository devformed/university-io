package com.lockermat.model.repository.base.impl;

import com.lockermat.model.repository.base.CriteriaQueries;
import com.lockermat.model.repository.base.JpaRepository2;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author Anton Gorokh
 */
@Transactional
public class JpaRepository2Impl<E, ID> extends SimpleJpaRepository<E, ID> implements JpaRepository2<E, ID> {

	private final JpaEntityInformation<E, ID> entityInfo;
	private final EntityManager entityManager;

	public JpaRepository2Impl(JpaEntityInformation<E, ID> entityInfo, EntityManager entityManager) {
		super(entityInfo, entityManager);
		this.entityInfo = entityInfo;
		this.entityManager = entityManager;
	}

	@Override
	public <S extends E> List<S> save(Iterable<S> entities) {
		return super.saveAll(entities);
	}

	@Override
	public <S extends E> List<S> saveAndFlush(Iterable<S> entities) {
		return super.saveAllAndFlush(entities);
	}

	@Override
	public List<E> findById(Iterable<ID> ids) {
		return super.findAllById(ids);
	}

	@Override
	public Optional<E> findAny(Specification<E> spec) {
		TypedQuery<E> query = super.getQuery(spec, Pageable.unpaged());
		return CriteriaQueries.anyValue(query).toOptional();
	}

	@Override
	public void delete(Iterable<? extends E> entities) {
		super.deleteAll(entities);
	}

	@Override
	public void deleteById(Iterable<? extends ID> ids) {
		super.deleteAllById(ids);
	}

	@Override
	public void deleteInBatchById(Iterable<ID> ids) {
		super.deleteAllByIdInBatch(ids);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void deleteInBatch(@NotNull Iterable<E> entities) {
		throw new UnsupportedOperationException("deprecated");
	}
}
