package com.templateproject.api;

import com.templateproject.api.entity.Role;
import com.templateproject.api.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}
	@Bean
	public CommandLineRunner run(
			RoleRepository roleRepository
	) {

		return (args) -> {

			if (roleRepository.findByName("ROLE_ADMIN").isEmpty()) {
				Role adminRole = new Role("ROLE_ADMIN");
				roleRepository.save(adminRole);
			}
			if (roleRepository.findByName("ROLE_USER").isEmpty()) {
				Role userRole = new Role("ROLE_USER");
				roleRepository.save(userRole);
			}
		};
	}
}
