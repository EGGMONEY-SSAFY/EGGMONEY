package com.ssafy.eggmoney.notification.controller;

import com.ssafy.eggmoney.notification.dto.request.NotificationRequest;
import com.ssafy.eggmoney.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class NotificationController {
    private final NotificationService notificationService;

    @PostMapping("/notification/send")
    public ResponseEntity<Void> sendNotification(@RequestHeader("Authorization") String token,
                                                 @RequestBody NotificationRequest notificationReq) {
        notificationService.saveNotification(1L, notificationReq);
        return ResponseEntity.ok().build();
    }
}
