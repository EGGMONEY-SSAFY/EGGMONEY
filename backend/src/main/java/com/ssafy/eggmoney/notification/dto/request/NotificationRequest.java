package com.ssafy.eggmoney.notification.dto.request;

import com.ssafy.eggmoney.notification.entity.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationRequest {
    private NotificationType notificationType;
    private String message;
    private Long receiveUserId;

    public NotificationRequest(NotificationType notificationType, String message) {
        this.notificationType = notificationType;
        this.message = message;
    }
}
