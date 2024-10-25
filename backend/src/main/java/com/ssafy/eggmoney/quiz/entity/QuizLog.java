package com.ssafy.eggmoney.quiz.entity;

import com.ssafy.eggmoney.common.entity.BaseTime;
import com.ssafy.eggmoney.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Table(name = "quiz_logs")
@Setter
@NoArgsConstructor(access = PROTECTED)
public class QuizLog extends BaseTime {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "quiz_log_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "quiz_id")
    Quiz quiz;

    Integer isRight;

    @Builder
    public QuizLog(Quiz quiz, User user, Integer isRight) {
        this.quiz = quiz;
        this.user = user;
        this.isRight = isRight;
    }
}
