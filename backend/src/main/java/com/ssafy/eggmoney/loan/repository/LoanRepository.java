package com.ssafy.eggmoney.loan.repository;

import com.ssafy.eggmoney.loan.entity.Loan;
import com.ssafy.eggmoney.loan.entity.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findAllByUserIdOrderByCreatedAtDesc(Long userId);
    Optional<Loan> findByIdAndLoanStatus(Long id, LoanStatus loanStatus);
}
