package com.temperature;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

// @EnableWebFlux
@SpringBootApplication
@ComponentScan("com.temperature")
@EntityScan("com.temperature.model")
@EnableJpaRepositories("com.temperature.repository")
public class TemperatureApplication {

	public static void main(String[] args) {
		SpringApplication.run(TemperatureApplication.class, args);
	}

}
