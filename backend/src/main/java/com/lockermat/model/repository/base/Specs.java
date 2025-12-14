package com.lockermat.model.repository.base;

import com.lockermat.model.entity.base.AbstractEntity;
import com.lockermat.model.entity.base.AbstractEntity_;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.metamodel.SingularAttribute;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.function.TriFunction;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;
import java.util.List;

import static com.lockermat.util.Utils.opt;

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

	public static <E> Specification<E> and(Collection<Specification<E>> specs) {
		return Specification.allOf(specs);
	}

	public static <E> Specification<E> not(Specification<E> spec) {
		return Specification.not(spec);
	}

	public static <E, V> Specification<E> equal(SingularAttribute<? super E, V> attribute, V value) {
		return (root, query, cb) -> cb.equal(root.get(attribute), value);
	}

	public static <E extends AbstractEntity, E2 extends AbstractEntity> Specification<E> equal(SingularAttribute<E, E2> attribute, Long id) {
		return (root, query, cb) -> cb.equal(root.get(attribute).get(AbstractEntity_.id), id);
	}

	public static <E extends AbstractEntity, E2 extends AbstractEntity> Specification<E> equal(SingularAttribute<E, E2> attribute, E2 entity) {
		return (root, query, cb) -> cb.equal(root.get(attribute).get(AbstractEntity_.id), entity.getId());
	}

	public static <E> Specification<E> equalIgnoreCase(SingularAttribute<? super E, String> attribute, String value) {
		return (root, query, cb) -> cb.equal(cb.lower(root.get(attribute)), cb.literal(opt(value, String::toLowerCase)));
	}

	public static <E, V> Specification<E> in(SingularAttribute<? super E, V> attribute, Collection<V> values) {
		return (root, query, cb) -> root.get(attribute).in(values);
	}

	public static <E> Specification<E> alwaysTrue() {
		return (root, query, cb) -> cb.conjunction();
	}

	public static <E> Specification<E> orderBy(TriFunction<CriteriaBuilder, CriteriaQuery<?>, From<?, E>, List<Order>> orders) {
		return (root, cq, cb) -> {
			cq.orderBy(orders.apply(cb, cq, root));
			return cb.conjunction();
		};
	}
}
