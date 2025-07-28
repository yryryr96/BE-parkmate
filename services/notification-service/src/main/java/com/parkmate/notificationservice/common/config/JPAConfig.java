package com.parkmate.notificationservice.common.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

@EnableJpaAuditing
@EnableJpaRepositories(basePackages = "com.parkmate.notificationservice.usertoken.*")
@Configuration
public class JPAConfig {

    /**
     * 여러 TransactionManager 중 JPA의 것을 기본으로 사용하도록 지정합니다.
     */
    @Primary
    @Bean
    public PlatformTransactionManager jpaTransactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    /**
     * 위에서 만든 기본 TransactionManager를 사용하여 TransactionTemplate Bean을 생성합니다.
     */
    @Bean
    public TransactionTemplate transactionTemplate(PlatformTransactionManager transactionManager) {
        return new TransactionTemplate(transactionManager);
    }
}
