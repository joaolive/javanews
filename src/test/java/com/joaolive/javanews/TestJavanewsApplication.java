package com.joaolive.javanews;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration(proxyBeanMethods = false)
@ActiveProfiles("test")
public class TestJavanewsApplication {

	@Bean
	@ServiceConnection(name = "postgres")
	PostgreSQLContainer<?> postgresContainer() {
		PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:18-alpine");	
		// Forces Testcontainers to map the internal port 5432 to the host machine's external port 5433
		container.setPortBindings(java.util.List.of("5433:5432"));
		return container;
	}

	public static void main(String[] args) {
		SpringApplication.from(JavanewsApplication::main)
				.with(TestJavanewsApplication.class)
				.run(args);
	}
}
