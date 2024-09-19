package com.ssafy.eggmoney.loan.repository;

import com.ssafy.eggmoney.loan.entity.LoanLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanLogRepository extends JpaRepository<LoanLog, Long> {
    List<LoanLog> findAllByLoanIdOrderByCreatedAtDesc(Long loanId);
}
