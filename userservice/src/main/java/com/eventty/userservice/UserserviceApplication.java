package com.eventty.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class UserserviceApplication {
	public static void main(String[] args) {
		SpringApplication.run(UserserviceApplication.class, args);
	}

}
