package com.ssafy.eggmoney.notification.controller;

import com.ssafy.eggmoney.notification.dto.request.NotificationRequest;
import com.ssafy.eggmoney.notification.dto.response.NotificationResponse;
import com.ssafy.eggmoney.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class NotificationController {
    private final NotificationService notificationService;

    @PostMapping("/notification/send")
    public ResponseEntity<Void> sendNotification(@RequestHeader("Authorization") String token,
                                                 @RequestBody NotificationRequest notificationReq) {
        notificationService.saveNotification(null, notificationReq);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/notification/list")
    public ResponseEntity<List<NotificationResponse>> getNotifications(@RequestHeader("Authorization") String token) {
        return new ResponseEntity<>(notificationService.findNotifications(1L), HttpStatus.OK);
    }
}
