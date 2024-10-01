package com.ssafy.eggmoney.loan.repository;

import com.ssafy.eggmoney.loan.entity.Loan;
import com.ssafy.eggmoney.loan.entity.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    @Query(value = "SELECT * FROM loans l WHERE l.user_id IN (:ids) ORDER BY l.created_at DESC", nativeQuery = true)
    List<Loan> findAllByUserIdOrderByCreatedAtDesc(@Param("ids") List<Long> userIds);

    Optional<Loan> findByIdAndLoanStatus(Long id, LoanStatus loanStatus);

    List<Long> findIdByLoanStatusAndExpirationDateBetween(LoanStatus loanStatus, LocalDateTime start, LocalDateTime end);
}
