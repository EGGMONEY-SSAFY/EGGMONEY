package com.ssafy.eggmoney.notification.entity;

import com.ssafy.eggmoney.common.entity.BaseTime;
import com.ssafy.eggmoney.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Table(name = "notifications")
@NoArgsConstructor(access = PROTECTED)
public class Notification extends BaseTime {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name ="send_user_id")
    private User sendUser;

    @NotNull
    @Enumerated(value = STRING)
    private NotificationType notificationType;

    @NotNull
    private String message;

    @NotNull
    private Boolean isRead;

    public Notification(User user, User sendUser, NotificationType notificationType, String message) {
        this.user = user;
        this.sendUser = sendUser;
        this.notificationType = notificationType;
        this.message = message;
        this.isRead = false;
    }

    public void readNotification() {
        this.isRead = true;
    }
}
