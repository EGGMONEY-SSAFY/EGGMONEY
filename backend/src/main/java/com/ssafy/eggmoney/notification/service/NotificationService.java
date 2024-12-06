package com.ssafy.eggmoney.notification.service;

import com.ssafy.eggmoney.notification.dto.request.NotificationRequest;
import com.ssafy.eggmoney.notification.dto.response.NotificationResponse;

import java.util.List;

public interface NotificationService {
    void saveNotification(Long sendUserId, NotificationRequest notificationReq);
    List<NotificationResponse> findNotifications(Long userId);
    void readNotification(Long notificationId);
}
