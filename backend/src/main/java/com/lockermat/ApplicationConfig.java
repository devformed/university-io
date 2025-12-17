package com.lockermat;

import com.lockermat.model.repository.base.impl.ModularJpaRepositoryFactoryBean;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Anton Gorokh
 */
@Configuration
@EnableScheduling
@EnableJpaAuditing
@EnableTransactionManagement
@ComponentScan(basePackages = "com.lockermat")
@EntityScan(basePackages = "com.lockermat.model")
@EnableJpaRepositories(basePackages = "com.lockermat.model.repository", repositoryFactoryBeanClass = ModularJpaRepositoryFactoryBean.class)
public class ApplicationConfig {
}
