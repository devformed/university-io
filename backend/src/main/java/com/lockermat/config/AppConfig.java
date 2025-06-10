package com.lockermat.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.context.annotation.Configuration;

/**
 * @author Anton Gorokh
 */
@Configuration
@EnableScheduling
@EnableJpaAuditing
@EnableTransactionManagement
@ComponentScan(basePackages = "com.lockermat")
@EntityScan(basePackages = "com.lockermat.model")
@EnableJpaRepositories(basePackages = "com.lockermat.model.repository")
public class AppConfig {
}
