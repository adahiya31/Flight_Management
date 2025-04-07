package com.flight.management.Worldline_Assignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FlightAppManagement {

	public static void main(String[] args) {
		SpringApplication.run(FlightAppManagement.class, args);
	}

}
