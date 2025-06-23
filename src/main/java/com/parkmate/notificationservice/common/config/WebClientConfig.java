package com.parkmate.notificationservice.common.config;

//import org.springframework.cloud.client.loadbalancer.LoadBalanced;

import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.time.Duration;

@Configuration
public class WebClientConfig {

    @Bean
    public ConnectionProvider connectionProvider() {
        return ConnectionProvider.builder("my-connection-pool")
                .maxConnections(500) // 🚨 동시에 유지할 최대 연결 수 (기본 50, 늘려야 함)
                .pendingAcquireMaxCount(5000) // 🚨 연결을 얻기 위해 대기할 수 있는 최대 큐 크기 (기본 1000, 늘려야 함)
                .maxIdleTime(Duration.ofSeconds(60)) // 연결 유휴 시간 (재사용 대기)
                .maxLifeTime(Duration.ofMinutes(5)) // 연결 최대 수명
                .pendingAcquireTimeout(Duration.ofSeconds(10)) // 연결 획득 대기 타임아웃
                .build();
    }

    @Bean
    @LoadBalanced
    public WebClient.Builder loadBalancedWebClientBuilder(ConnectionProvider connectionProvider) {
        HttpClient httpClient = HttpClient.create(connectionProvider) // 🚨 ConnectionProvider 주입
                .responseTimeout(Duration.ofSeconds(30)) // 응답 전체 타임아웃 30초 (연결 후 응답까지)
                .doOnConnected(conn -> conn
                        .addHandlerLast(new ReadTimeoutHandler(30)) // 읽기 타임아웃 30초
                        .addHandlerLast(new WriteTimeoutHandler(30))); // 쓰기 타임아웃 30초

        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient));
    }

    @Bean
    public WebClient webClientBuilder(WebClient.Builder loadBalancedWebClientBuilder) {
        return loadBalancedWebClientBuilder.build();
    }
}
