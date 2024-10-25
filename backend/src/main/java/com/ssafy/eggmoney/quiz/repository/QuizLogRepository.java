package com.ssafy.eggmoney.quiz.repository;

import com.ssafy.eggmoney.quiz.entity.QuizLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizLogRepository extends JpaRepository<QuizLog, Long> {
}
