package com.lockermat.model.repository;

import com.lockermat.model.entity.AbstractEntity;
import com.lockermat.model.entity.AbstractEntity_;
import jakarta.persistence.metamodel.SingularAttribute;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

/**
 * @author Anton Gorokh
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Specs {

	@SafeVarargs
	public static <E> Specification<E> or(Specification<E>... specs) {
		return Specification.anyOf(specs);
	}

	@SafeVarargs
	public static <E> Specification<E> and(Specification<E>... specs) {
		return Specification.allOf(specs);
	}

	public static <E> Specification<E> not(Specification<E> spec) {
		return Specification.not(spec);
	}

	public static <E, V> Specification<E> has(SingularAttribute<? super E, V> attribute, V value) {
		return (root, query, cb) -> cb.equal(root.get(attribute), value);
	}

	public static <E extends AbstractEntity, E2 extends AbstractEntity> Specification<E> has(SingularAttribute<E, E2> attribute, UUID id) {
		return (root, query, cb) -> cb.equal(root.get(attribute).get(AbstractEntity_.id), id);
	}

	public static <E extends AbstractEntity, E2 extends AbstractEntity> Specification<E> has(SingularAttribute<E, E2> attribute, E2 entity) {
		return (root, query, cb) -> cb.equal(root.get(attribute).get(AbstractEntity_.id), entity.getId());
	}

	public static <E, V extends Comparable<V>> Specification<E> lt(SingularAttribute<? super E, V> attribute, V value) {
		return (root, query, cb) -> cb.lessThan(root.get(attribute), value);
	}

	public static <E, V extends Comparable<V>> Specification<E> gt(SingularAttribute<? super E, V> attribute, V value) {
		return (root, query, cb) -> cb.greaterThan(root.get(attribute), value);
	}
}
