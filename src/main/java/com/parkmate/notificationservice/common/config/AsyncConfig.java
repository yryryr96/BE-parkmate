package com.parkmate.notificationservice.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class AsyncConfig {

    @Bean
    public ThreadPoolTaskExecutor dbThreadPool() {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(50);
        executor.setThreadNamePrefix("DB-Async-");

        return executor;
    }

    @Bean
    public ThreadPoolTaskExecutor fcmThreadPool() {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(50);
        executor.setThreadNamePrefix("FCM-Async-");

        return executor;
    }
}
