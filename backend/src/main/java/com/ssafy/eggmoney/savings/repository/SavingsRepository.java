package com.ssafy.eggmoney.savings.repository;


import com.ssafy.eggmoney.savings.entity.Savings;
import com.ssafy.eggmoney.savings.entity.SavingsStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SavingsRepository extends JpaRepository<Savings, Long> {
    Optional<Savings> findByUserIdAndSavingsStatus(Long userId, SavingsStatus savingsStatus);

    Optional<Savings> findByIdAndSavingsStatus(Long savingsId, SavingsStatus savingsStatus);
}
