package com.parkmate.parkingreadservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class ParkingreadserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParkingreadserviceApplication.class, args);
	}

}
