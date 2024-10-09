package com.ssafy.eggmoney.deposit.repository;

import com.ssafy.eggmoney.deposit.entity.Deposit;
import com.ssafy.eggmoney.deposit.entity.DepositStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;


public interface DepositRepository extends JpaRepository<Deposit, Long> {
    Optional<Deposit> findByUserIdAndDepositStatus(Long userId, DepositStatus status);

    @Query(value = "SELECT d.id FROM Deposit d WHERE d.expireDate BETWEEN :start AND :end AND d.depositStatus = :depositStatus")
    List<Long> findIdByExpireDateBetweenAndDepositStatus(LocalDateTime start, LocalDateTime end, DepositStatus depositStatus);

    Optional<Deposit> findByIdAndDepositStatus(long depositId, DepositStatus depositStatus);

    List<Deposit> findAllByExpireDateBetweenAndDepositStatus(LocalDateTime start,LocalDateTime end, DepositStatus depositStatus);
}

