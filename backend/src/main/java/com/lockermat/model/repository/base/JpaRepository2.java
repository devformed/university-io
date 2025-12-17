package com.lockermat.model.repository.base;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

/**
 * A refined variant of {@link org.springframework.data.jpa.repository.JpaRepository}
 * that intentionally omits deprecated or potentially hazardous methods such as
 * unconditional {@code findAll()} or {@code deleteAll()} operations.
 *
 * @author Anton Gorokh
 */
@NoRepositoryBean
public interface JpaRepository2<E, ID> extends Repository<E, ID>, ExtendedJpaSpecificationExecutor<E> {

    void flush();

    // upsert

    <S extends E> S save(S entity);

    <S extends E> List<S> save(Iterable<S> entities);

    <S extends E> S saveAndFlush(S entity);

    <S extends E> List<S> saveAndFlush(Iterable<S> entities);

    // read

    E getReferenceById(ID id);

    Optional<E> findById(ID id);

    List<E> findById(Iterable<ID> ids);

    boolean existsById(ID id);

    long count();

    // delete

    void delete(Iterable<? extends E> entities);

    void delete(E entity);

    void deleteById(Iterable<? extends ID> ids);

    void deleteById(ID id);

    void deleteInBatch(Iterable<E> entities);

    void deleteInBatchById(Iterable<ID> ids);
}
