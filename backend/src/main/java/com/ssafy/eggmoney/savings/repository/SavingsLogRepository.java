package com.ssafy.eggmoney.savings.repository;

import com.ssafy.eggmoney.savings.entity.SavingsLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface SavingsLogRepository extends JpaRepository<SavingsLog, Long> {
    List<SavingsLog> findAllBySavingsIdOrderByCreatedAtDesc(Long savingsId);

    @Query(value = "SELECT DISTINCT log.savings.id FROM SavingsLog log WHERE log.createdAt BETWEEN :start AND :end")
    List<Long> findSavingsIdByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}
