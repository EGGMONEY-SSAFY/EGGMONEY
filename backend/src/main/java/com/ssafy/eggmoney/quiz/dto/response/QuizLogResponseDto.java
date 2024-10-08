package com.ssafy.eggmoney.quiz.dto.response;

import com.ssafy.eggmoney.quiz.entity.Quiz;
import com.ssafy.eggmoney.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QuizLogResponseDto {
    Integer isRight;
    Long userId;
    User user;
    Quiz quiz;
}
