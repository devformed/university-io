package com.lockermat.model.repository.base;

import com.lockermat.model.entity.base.AbstractEntity;
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
public interface JpaRepository2<E extends AbstractEntity> extends Repository<AbstractEntity, Long>, ExtendedJpaSpecificationExecutor<E> {

    void flush();

    // upsert

    <S extends E> S save(S entity);

    <S extends E> List<S> save(Iterable<S> entities);

    <S extends E> S saveAndFlush(S entity);

    <S extends E> List<S> saveAndFlush(Iterable<S> entities);

    // read

    E getReferenceById(Long id);

    Optional<E> findById(Long id);

    List<E> findById(Iterable<Long> ids);

    boolean existsById(Long id);

    long count();

    // delete

    void delete(Iterable<? extends E> entities);

    void delete(E entity);

    void deleteById(Iterable<? extends Long> ids);

    void deleteById(Long id);

    void deleteInBatch(Iterable<E> entities);

    void deleteInBatchById(Iterable<Long> ids);
}
