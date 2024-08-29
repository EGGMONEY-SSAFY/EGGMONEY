package com.example.alrambot.config;

import com.example.alrambot.service.NotificationService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class SchedulerConfig {

    private final NotificationService notificationService;

    public SchedulerConfig(NotificationService notificationService){
        this.notificationService = notificationService;
    }

    @Scheduled(cron = "0 0 18 * * *", zone = "Asia/Seoul")
    public void sendCheckinNotification(){
        notificationService.sendNotification("퇴실 체크 알림 @na6037 @bayleaf07 @widwldud0601 @asdf1916 @gotwo6141922 @kwakjaeeun ");
    }

    @Scheduled(cron = "0 30 8 * * *", zone = "Asia/Seoul")
    public void sendNotification() {
        notificationService.sendNotification("입실 체크 알림 @na6037 @bayleaf07 @widwldud0601 @asdf1916 @gotwo6141922 @kwakjaeeun ");
    }

}
