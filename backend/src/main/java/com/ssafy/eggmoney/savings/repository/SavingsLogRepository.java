package com.ssafy.eggmoney.savings.repository;

import com.ssafy.eggmoney.savings.entity.SavingsLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SavingsLogRepository extends JpaRepository<SavingsLog, Long> {
    List<SavingsLog> findAllBySavingsIdOrderByCreatedAtDesc(Long savingsId);
    List<Long> findSavingsIdByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}
