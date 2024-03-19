package com.eventty.businessservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
public class BusinessserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BusinessserviceApplication.class, args);
	}

}
