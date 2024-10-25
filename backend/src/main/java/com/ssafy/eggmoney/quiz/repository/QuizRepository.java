package com.ssafy.eggmoney.quiz.repository;

import com.ssafy.eggmoney.quiz.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    @Query(value = "SELECT * FROM quizs ORDER BY RAND() LIMIT 5", nativeQuery = true)
    List<Quiz> find5Quiz();
}
