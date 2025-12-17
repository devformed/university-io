package com.lockermat.model.repository.base.impl;

import com.lockermat.model.entity.base.AbstractEntity;
import jakarta.persistence.EntityManager;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

/**
 * @author Anton Gorokh
 */
public class ModularJpaRepositoryFactoryBean<E extends AbstractEntity, REPO extends JpaRepository<E, Long>> extends JpaRepositoryFactoryBean<REPO, E, Long> {

	public ModularJpaRepositoryFactoryBean(Class<? extends REPO> repositoryInterface) {
		super(repositoryInterface);
	}

	@Override
	protected @NotNull RepositoryFactorySupport createRepositoryFactory(@NotNull EntityManager entityManager) {
		return new ModularJpaRepositoryFactory(entityManager);
	}
}
