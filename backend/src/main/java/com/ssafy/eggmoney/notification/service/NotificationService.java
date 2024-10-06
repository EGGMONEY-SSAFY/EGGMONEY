package com.ssafy.eggmoney.notification.service;

import com.ssafy.eggmoney.notification.dto.request.NotificationRequest;

public interface NotificationService {
    void saveNotification(Long userId, NotificationRequest notificationReq);
}
