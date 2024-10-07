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

    @Query(value = "SELECT l FROM Loan l WHERE l.user.id IN (:ids) ORDER BY l.createdAt DESC")
    List<Loan> findAllByUserIdOrderByCreatedAtDesc(@Param("ids") List<Long> userIds);

    Optional<Loan> findByIdAndLoanStatus(Long id, LoanStatus loanStatus);

    @Query(value = "SELECT l.id FROM Loan l WHERE l.loanStatus = :loanStatus AND l.expirationDate BETWEEN :start AND :end")
    List<Long> findIdByLoanStatusAndExpirationDateBetween(LoanStatus loanStatus, LocalDateTime start, LocalDateTime end);
}
