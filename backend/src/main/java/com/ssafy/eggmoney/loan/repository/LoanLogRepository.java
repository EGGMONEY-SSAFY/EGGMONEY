package com.ssafy.eggmoney.loan.repository;

import com.ssafy.eggmoney.loan.entity.LoanLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanLogRepository extends JpaRepository<LoanLog, Long> {
}
