package com.ssafy.eggmoney.notification.controller;

import com.ssafy.eggmoney.auth.service.KakaoAuthService;
import com.ssafy.eggmoney.notification.dto.request.NotificationRequest;
import com.ssafy.eggmoney.notification.dto.response.NotificationResponse;
import com.ssafy.eggmoney.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class NotificationController {
    private final NotificationService notificationService;
    private final KakaoAuthService kakaoAuthService;

    @PostMapping("/notification/send")
    public ResponseEntity<Void> sendNotification(@RequestHeader("Authorization") String token,
                                                 @RequestBody NotificationRequest notificationReq) {
        Long sendUserId = kakaoAuthService.verifyKakaoToken(token).getId();
        notificationService.saveNotification(sendUserId, notificationReq);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/notification/list")
    public ResponseEntity<List<NotificationResponse>> getNotifications(@RequestHeader("Authorization") String token) {
        Long userId = kakaoAuthService.verifyKakaoToken(token).getId();
        log.info("Controller userID = {}",userId);
        return new ResponseEntity<>(notificationService.findNotifications(userId), HttpStatus.OK);
    }

    @PostMapping("/notification/{notificationId}/read")
    public ResponseEntity<Void> readNotification(@RequestHeader("Authorization") String token,
                                                 @PathVariable Long notificationId) {
        kakaoAuthService.verifyKakaoToken(token);
        notificationService.readNotification(notificationId);
        return ResponseEntity.ok().build();
    }
}
