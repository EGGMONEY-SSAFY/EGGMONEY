package com.ssafy.eggmoney.loan.repository;

import com.ssafy.eggmoney.loan.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {
}
