package com.parkmate.parkingreadservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableKafka
@EnableAsync
@EnableScheduling
@EnableDiscoveryClient
@SpringBootApplication
public class ParkingreadserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ParkingreadserviceApplication.class, args);
    }

}
