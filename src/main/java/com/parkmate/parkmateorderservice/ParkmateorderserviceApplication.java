package com.parkmate.parkmateorderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ParkmateorderserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParkmateorderserviceApplication.class, args);
	}

}
