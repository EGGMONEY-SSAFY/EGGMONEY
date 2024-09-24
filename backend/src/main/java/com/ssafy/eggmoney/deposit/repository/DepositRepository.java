package com.ssafy.eggmoney.deposit.repository;

import com.ssafy.eggmoney.deposit.entity.Deposit;
import com.ssafy.eggmoney.deposit.entity.DepositStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface DepositRepository extends JpaRepository<Deposit, Long> {
    Optional<Deposit> findByUserIdAndDepositStatus(Long userId, DepositStatus status);

    Optional<Deposit> findByIdAndDepositStatus(long depositId, DepositStatus depositStatus);
}

