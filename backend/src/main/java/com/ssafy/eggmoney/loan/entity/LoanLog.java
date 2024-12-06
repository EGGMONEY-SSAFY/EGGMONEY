package com.ssafy.eggmoney.loan.entity;

import com.ssafy.eggmoney.common.entity.BaseTime;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Table(name = "loan_logs")
@NoArgsConstructor(access = PROTECTED)
public class LoanLog extends BaseTime {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "loan_log_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_id")
    private Loan loan;
    private int repayment; // 상환금액
    private int balance;

    @Builder
    public LoanLog(Long id, Loan loan, int balance, int repayment) {
        this.id = id;
        this.loan = loan;
        this.balance = balance;
        this.repayment = repayment;
    }
}
