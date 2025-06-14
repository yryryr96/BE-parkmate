package com.parkmate.parkingreadservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableKafka
@EnableAsync
@EnableDiscoveryClient
@EnableMongoRepositories(basePackages = "com.parkmate.parkingreadservice")
@SpringBootApplication
public class ParkingreadserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParkingreadserviceApplication.class, args);
	}

}
