package com.ssafy.eggmoney.quiz.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QuizResponseDto {
    String content;
    String select1;
    String select2;
    String select3;
    String select4;
    String answer;
}
