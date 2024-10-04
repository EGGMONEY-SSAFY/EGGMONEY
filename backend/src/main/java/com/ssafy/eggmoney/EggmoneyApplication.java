package com.ssafy.eggmoney;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

// test1133333
@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
public class EggmoneyApplication {
	public static void main(String[] args) {
		SpringApplication.run(EggmoneyApplication.class, args);
	}
}
