package com.ssafy.eggmoney.quiz.entity;

import com.ssafy.eggmoney.common.entity.BaseTime;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Table(name = "quizs")
@Setter
@NoArgsConstructor(access = PROTECTED)
public class Quiz extends BaseTime {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "quiz_id")
    private Long id;

    String content;
    String select1;
    String select2;
    String select3;
    String select4;
    String answer;

    @Builder
    public Quiz(String content, String select1, String select2, String select3, String select4, String answer) {
        this.content = content;
        this.select1 = select1;
        this.select2 = select2;
        this.select3 = select3;
        this.select4 = select4;
        this.answer = answer;
    }
}
