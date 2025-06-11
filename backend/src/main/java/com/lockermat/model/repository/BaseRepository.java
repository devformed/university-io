package com.lockermat.model.repository;

import com.lockermat.model.entity.AbstractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.UUID;

/**
 * @author Anton Gorokh
 */
@NoRepositoryBean
public interface BaseRepository<T extends AbstractEntity> extends JpaRepository<T, UUID>, JpaSpecificationExecutor<T> {
}
