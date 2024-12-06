package com.ssafy.eggmoney.loan.entity;

import com.ssafy.eggmoney.common.entity.BaseTime;
import com.ssafy.eggmoney.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Table(name = "loans")
@NoArgsConstructor(access = PROTECTED)
public class Loan extends BaseTime {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "loan_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(value = STRING)
    private LoanType loanType;

    @Enumerated(value = STRING)
    private LoanStatus loanStatus;

    private int loanAmount;
    private int loanDate; // 개월 수
    private int balance; // 만기일시상환 시에는 loanAmount와 동일
    private String loanReason;
    private String refuseReason;
    private Double loanRate;
    private LocalDateTime expirationDate;


    @Builder(toBuilder = true)
    public Loan(Long id, User user, LoanType loanType, LoanStatus loanStatus, int loanAmount, int loanDate, int balance, String loanReason, String refuseReason, Double loanRate, LocalDateTime expirationDate) {
        this.id = id;
        this.user = user;
        this.loanType = loanType;
        this.loanStatus = loanStatus;
        this.loanAmount = loanAmount;
        this.loanDate = loanDate;
        this.balance = balance;
        this.loanReason = loanReason;
        this.refuseReason = refuseReason;
        this.loanRate = loanRate;
        this.expirationDate = expirationDate;
    }
}
