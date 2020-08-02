package com.wander.codechallenge;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WanderApplication {

	public static void main(String[] args) {
		SpringApplication.run(WanderApplication.class, args);
	}

	@Bean
	CommandLineRunner init(com.wander.codechallenge.repositories.RoleRepository roleRepository) {

		return args -> {

			com.wander.codechallenge.models.Role adminRole = roleRepository.findByRole("ADMIN");
			if (adminRole == null) {
				com.wander.codechallenge.models.Role newAdminRole = new com.wander.codechallenge.models.Role();
				newAdminRole.setRole("ADMIN");
				roleRepository.save(newAdminRole);
			}

			com.wander.codechallenge.models.Role userRole = roleRepository.findByRole("USER");
			if (userRole == null) {
				com.wander.codechallenge.models.Role newUserRole = new com.wander.codechallenge.models.Role();
				newUserRole.setRole("USER");
				roleRepository.save(newUserRole);
			}
		};

	}
}
