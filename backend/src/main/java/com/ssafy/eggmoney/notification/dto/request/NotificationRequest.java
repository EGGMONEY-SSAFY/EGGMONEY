package com.ssafy.eggmoney.notification.dto.request;

import com.ssafy.eggmoney.notification.entity.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NotificationRequest {
    private NotificationType notificationType;
    private String message;
    private Long receiveUser;


    @Builder
    public NotificationRequest(NotificationType notificationType, String message, Long receiveUser) {
        this.notificationType = notificationType;
        this.message = message;
        this.receiveUser = receiveUser;
    }



    public NotificationRequest(NotificationType notificationType, String message) {
        this.notificationType = notificationType;
        this.message = message;
    }
}
