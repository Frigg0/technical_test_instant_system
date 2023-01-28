package com.frg.technical_test_instant_system;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class TechnicalTestInstantSystemApplication {

	public static void main(String[] args) {

		SpringApplication.run(TechnicalTestInstantSystemApplication.class, args);

		// LifeCycle Logging
		log.warn("----- Application Started -----");

		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			log.warn("----- Application Started -----");
		}));
	}
}
