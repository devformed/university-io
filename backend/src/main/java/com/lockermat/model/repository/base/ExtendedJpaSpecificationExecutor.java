package com.lockermat.model.repository.base;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ExtendedJpaSpecificationExecutor<E> extends JpaSpecificationExecutor<E> {

    Optional<E> findAny(Specification<E> spec);

    default Optional<E> findAny(Specification<E> spec1, Specification<E> spec2) {
        return findAny(Specs.and(spec1, spec2));
    }

    default Optional<E> findAny(Specification<E> spec1, Specification<E> spec2, Specification<E> spec3) {
        return findAny(Specs.and(spec1, spec2, spec3));
    }

    default E getOne(Specification<E> spec) {
        return findOne(spec).orElseThrow();
    }
}
