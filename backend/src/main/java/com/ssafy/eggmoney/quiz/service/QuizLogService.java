package com.ssafy.eggmoney.quiz.service;

import com.ssafy.eggmoney.quiz.dto.response.QuizLogResponseDto;
import com.ssafy.eggmoney.quiz.entity.Quiz;
import com.ssafy.eggmoney.quiz.entity.QuizLog;
import com.ssafy.eggmoney.quiz.repository.QuizLogRepository;
import com.ssafy.eggmoney.quiz.repository.QuizRepository;
import com.ssafy.eggmoney.user.entity.User;
import com.ssafy.eggmoney.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuizLogService {
    private final QuizLogRepository quizLogRepository;
    private final QuizRepository quizRepository;
    private final UserRepository userRepository;

    public QuizLogResponseDto getQuizLog(Long quizLogId) {
        Optional<QuizLog> oq = quizLogRepository.findById(quizLogId);
        QuizLog quizLog = null;
        if ( oq.isPresent() ) {
            quizLog =  oq.get();
        }
        else {
            throw new NoSuchElementException("존재하지 않는 퀴즈로그입니다.");
        }
        return QuizLogResponseDto.builder()
                .userId(quizLog.getUser().getId())
                .isRight(quizLog.getIsRight())
                .build();
    }

    public void createQuizLog(Long quizId, Long userId, Integer isRight) {
        Optional<Quiz> quiz = quizRepository.findById(quizId);
        Optional<User> user = userRepository.findById(userId);
        if ( !quiz.isPresent() && !user.isPresent() ) {
            throw new NoSuchElementException("조회할 데이터가 없습니다.");
        }

        quizLogRepository.save(QuizLog
                .builder()
                        .quiz(quiz.get())
                        .isRight(isRight)
                        .user(user.get())
                .build());
    }

    public List<Quiz> getQuizList(){
        return quizRepository.find5Quiz();
    }
}
