package com.ssafy.eggmoney.loan.entity;

import com.ssafy.eggmoney.common.entity.BaseTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    private int loanAmount;
    private String loanType;
    private int loanDate;
    private int balance;
    private String loanReason;
    private String refuseReason;
    private int loanStatus;
    private Double loanRate;

    // 신청일시 : created_at

}
