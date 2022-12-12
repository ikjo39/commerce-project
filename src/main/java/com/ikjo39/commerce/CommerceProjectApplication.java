package com.ikjo39.commerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CommerceProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommerceProjectApplication.class, args);
	}

}
