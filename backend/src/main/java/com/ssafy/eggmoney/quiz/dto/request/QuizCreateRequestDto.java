package com.ssafy.eggmoney.quiz.dto.request;

import lombok.Getter;

@Getter
public class QuizCreateRequestDto {
    String content;
    String select1;
    String select2;
    String select3;
    String select4;
    String answer;
}
