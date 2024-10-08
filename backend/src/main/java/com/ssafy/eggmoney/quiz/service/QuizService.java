package com.ssafy.eggmoney.quiz.service;

import com.ssafy.eggmoney.quiz.dto.request.QuizCreateRequestDto;
import com.ssafy.eggmoney.quiz.dto.request.QuizJudgeRequestDto;
import com.ssafy.eggmoney.quiz.entity.Quiz;
import com.ssafy.eggmoney.quiz.repository.QuizLogRepository;
import com.ssafy.eggmoney.quiz.repository.QuizRepository;
import com.ssafy.eggmoney.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuizLogService quizLogService;

    public Quiz getQuiz(Long quizId) {
        Optional<Quiz> quiz = quizRepository.findById(quizId);
        Quiz q = null;
        if ( quiz.isPresent() ) {
            q = quiz.get();
        }
        return q;
    }

    public List<Quiz> getQuizList() {
        return quizRepository.find5Quiz();
    }

    public void createQuiz(QuizCreateRequestDto dto) {
        quizRepository.save( Quiz.builder()
                        .content(dto.getContent())
                        .select1(dto.getSelect1())
                        .select2(dto.getSelect2())
                        .select3(dto.getSelect3())
                        .select4(dto.getSelect4())
                        .answer(dto.getAnswer())
                .build());
    }

    public void deleteQuiz(Long quizId) {
        Optional<Quiz> quiz = quizRepository.findById(quizId);
        if ( quiz.isPresent() ) {
            quizRepository.delete(quiz.get());
        }
    }

    public void judgeQuiz(User user, Long quizId, QuizJudgeRequestDto dto) {
        Optional<Quiz> quiz = quizRepository.findById(quizId);
        if ( !quiz.isPresent() ) {
            throw new NoSuchElementException("조회할 퀴즈가 없습니다.");
        }

        Quiz q = quiz.get();
        Integer answer = dto.getAnswer();
        quizLogService.createQuizLog(q.getId(), user.getId(), answer);
    }

}
