package com.ssafy.eggmoney.notification.dto.request;

import com.ssafy.eggmoney.notification.entity.NotificationType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NotificationRequest {
    private NotificationType notificationType;
    private String message;
    private Long receiveUserId;


    @Builder
    public NotificationRequest(NotificationType notificationType, String message, Long receiveUserId) {
        this.notificationType = notificationType;
        this.message = message;
        this.receiveUserId = receiveUserId;
    }



    public NotificationRequest(NotificationType notificationType, String message) {
        this.notificationType = notificationType;
        this.message = message;
    }
}
