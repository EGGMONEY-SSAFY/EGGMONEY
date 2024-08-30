package com.example.alrambot;

import com.example.alrambot.service.NotificationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AlrambotApplication {

	private final NotificationService notificationService;

	public AlrambotApplication(NotificationService notificationService){
		this.notificationService = notificationService;
	}

	public static void main(String[] args) {
		SpringApplication.run(AlrambotApplication.class, args);
	}

	@Bean
	public CommandLineRunner sendTestMessage(){
		return args -> {
			notificationService.sendNotification("@na6037 테스트 메시지 전송");
		};
	}
}
