package com.ssafy.eggmoney.savings.repository;


import com.ssafy.eggmoney.savings.entity.Savings;
import com.ssafy.eggmoney.savings.entity.SavingsStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SavingsRepository extends JpaRepository<Savings, Long> {
    Optional<Savings> findByUserIdAndSavingsStatus(Long userId, SavingsStatus savingsStatus);

    Optional<Savings> findByIdAndSavingsStatus(Long savingsId, SavingsStatus savingsStatus);

    @Query(value = "SELECT s.id FROM Savings s WHERE s.expireDate BETWEEN :start AND :end AND s.savingsStatus = (:savingsStatus)")
    List<Long> findIdByExpireDateBetweenAndSavingsStatus(LocalDateTime start, LocalDateTime end, SavingsStatus savingsStatus);

    @Query(value = "SELECT s.id FROM Savings s WHERE s.savingsStatus = :savingsStatus AND s.paymentDate != (:paymentDate)")
    List<Long> findIdBySavingsStatusAndPaymentDateNot(SavingsStatus savingsStatus, int paymentDate);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Savings s SET s.expire_date = DATE_ADD(s.expire_date, INTERVAL 1 MONTH) WHERE s.savings_id IN (:ids)", nativeQuery = true)
    void extendSavingsExpireDateByOneMonth(@Param("ids") List<Long> savingsIds);
}
