package com.ssafy.eggmoney.notification.dto.response;

import com.ssafy.eggmoney.notification.entity.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class NotificationResponse {
    private Long sendUserId;
    private String SendUsername;
    private NotificationType notificationType;
    private String message;
    private boolean isRead;
    private LocalDateTime date;
}
