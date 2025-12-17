package com.lockermat.model.repository.base.impl;

import com.lockermat.model.repository.base.JpaRepository2;
import jakarta.persistence.EntityManager;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryComposition.RepositoryFragments;
import org.springframework.data.repository.core.support.RepositoryFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Anton Gorokh
 */
public class ModularJpaRepositoryFactory extends JpaRepositoryFactory {
	
	private final EntityManager em;

	public ModularJpaRepositoryFactory(EntityManager entityManager) {
		super(entityManager);
		this.em = entityManager;
	}

	@Override
	protected @NotNull RepositoryFragments getRepositoryFragments(RepositoryMetadata metadata) {
		Class<?> repositoryInterface = metadata.getRepositoryInterface();
		JpaEntityInformation<?, Object> entityInfo = super.getEntityInformation(metadata.getDomainType());

		return getRepositoryFragments(repositoryInterface, entityInfo)
				.stream()
				.reduce(super.getRepositoryFragments(metadata), RepositoryFragments::append, RepositoryFragments::append);
	}

	private <E, ID> List<RepositoryFragment<?>> getRepositoryFragments(Class<?> repositoryInterface, JpaEntityInformation<E, ID> entityInfo) {
		List<RepositoryFragment<?>> fragments = new ArrayList<>();
		if (JpaRepository2.class.isAssignableFrom(repositoryInterface)) {
			fragments.add(RepositoryFragment.implemented(JpaRepository2.class, new JpaRepository2Impl<>(entityInfo, em)));
		}
		return fragments;
	}
}
