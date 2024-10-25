package com.ssafy.eggmoney.quiz.controller;

import com.ssafy.eggmoney.quiz.dto.response.QuizLogResponseDto;
import com.ssafy.eggmoney.quiz.service.QuizLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/quizlog")
public class QuizLogController {
    private final QuizLogService quizLogService;

    @GetMapping("/{quizLogId}")
    public QuizLogResponseDto getQuizLog(@PathVariable("quizLogId") Long quizLogId){
        return quizLogService.getQuizLog(quizLogId);
    }

}
