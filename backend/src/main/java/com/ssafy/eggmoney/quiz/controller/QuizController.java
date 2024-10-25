package com.ssafy.eggmoney.quiz.controller;

import com.ssafy.eggmoney.auth.service.KakaoAuthService;
import com.ssafy.eggmoney.quiz.dto.request.QuizCreateRequestDto;
import com.ssafy.eggmoney.quiz.dto.request.QuizJudgeRequestDto;
import com.ssafy.eggmoney.quiz.entity.Quiz;
import com.ssafy.eggmoney.quiz.service.QuizService;
import com.ssafy.eggmoney.user.entity.User;
import jakarta.persistence.Table;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/quiz")
public class QuizController {

    private final QuizService quizService;
    private final KakaoAuthService kakaoAuthService;

    @GetMapping("/{quizId}")
    public Quiz getQuiz(@PathVariable("quiz") Long quizId) {
        return quizService.getQuiz(quizId);
    }

    @GetMapping("/")
    public List<Quiz> getQuizList(){
        return quizService.getQuizList();
    }

    @PostMapping("/create")
    public void createQuiz(@RequestBody QuizCreateRequestDto dto) {
        quizService.createQuiz(dto);
    }

    @PostMapping("/delete/{quizId}")
    public void deleteQuiz(@PathVariable("quiz") Long quizId) {
        quizService.deleteQuiz(quizId);
    }

    @PostMapping("/judge/{quizId}")
    public void judgeQuiz(@RequestHeader(value = "Authorization") String token,
                          @PathVariable("quizId") Long quizId, @RequestBody QuizJudgeRequestDto dto){
        User user = kakaoAuthService.verifyKakaoToken(token);
        quizService.judgeQuiz(user, quizId, dto);
    }
}
