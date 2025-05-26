package com.parkmate.parking_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@EnableDiscoveryClient
@SpringBootApplication
public class ParkingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParkingServiceApplication.class, args);
	}

}
