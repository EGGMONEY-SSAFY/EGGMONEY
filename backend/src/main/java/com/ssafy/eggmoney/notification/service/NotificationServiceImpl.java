package com.ssafy.eggmoney.notification.service;

import com.ssafy.eggmoney.notification.dto.request.NotificationRequest;
import com.ssafy.eggmoney.notification.dto.response.NotificationResponse;
import com.ssafy.eggmoney.notification.entity.Notification;
import com.ssafy.eggmoney.notification.repository.NotificationRepository;
import com.ssafy.eggmoney.user.entity.User;
import com.ssafy.eggmoney.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly=true)
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public void saveNotification(Long sendUserId, NotificationRequest notificationReq) {
        User receiveUser;
        User sendUser = null;

        if(sendUserId != null) {
            sendUser = userRepository.findJoinFamilyById(sendUserId)
                    .orElseThrow(() -> new NoSuchElementException("[알림] 해당 유저를 찾을 수 없습니다."));
            if(sendUser.getRole().equals("부모")) {
                receiveUser = userRepository.findById(notificationReq.getReceiveUserId())
                        .orElseThrow(() -> new NoSuchElementException("[알림] 해당 유저를 찾을 수 없습니다."));
            } else {
                receiveUser = userRepository.findById(sendUser.getFamily().getPresentId())
                        .orElseThrow(() -> new NoSuchElementException("[알림] 해당 유저를 찾을 수 없습니다."));
            }
        } else {
            receiveUser = userRepository.findById(notificationReq.getReceiveUserId())
                    .orElseThrow(() -> new NoSuchElementException("[알림] 해당 유저를 찾을 수 없습니다."));
        }

        notificationRepository.save(new Notification(
                receiveUser, sendUser, notificationReq.getNotificationType(), notificationReq.getMessage()
        ));
    }

    @Override
    public List<NotificationResponse> findNotifications(Long userId) {
        List<Notification> notifications = notificationRepository.findJoinSendUserByUserId(userId);
        System.out.println(notifications);

        if(notifications.isEmpty()) {
            throw new NoSuchElementException("[알림] 알림들을 찾을 수 없습니다.");
        }

        return notifications.stream().map(n -> {
            if(n.getSendUser() != null) {
                return new NotificationResponse(
                    n.getId(), n.getSendUser().getId(), n.getSendUser().getName(), n.getNotificationType(),
                        n.getMessage(), n.getIsRead(), n.getCreatedAt()
                );
            } else  {
                return new NotificationResponse(
                        n.getId(), null, "에그머니", n.getNotificationType(),
                        n.getMessage(), n.getIsRead(), n.getCreatedAt()
                );
            }
        }).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void readNotification(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NoSuchElementException("[알림] 해당 알림을 찾을 수 없습니다."));

        notification.readNotification();
    }

}
