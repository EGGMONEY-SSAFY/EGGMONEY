package com.ssafy.eggmoney.account.entity;

import com.ssafy.eggmoney.common.entity.BaseTime;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Table(name = "account_logs")
@NoArgsConstructor(access = PROTECTED)
public class AccountLog extends BaseTime {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "account_log_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

//    현재 보유 금액
    private int currentBalance;
//    거래 가격
    private int tradePrice;
//    거래 대상
    private AccountLogType tradeTarget;

    @Builder
    private AccountLog(Account account, int currentBalance, int tradePrice, AccountLogType tradeTarget) {
        this.account = account;
        this.currentBalance = currentBalance;
        this.tradePrice = tradePrice;
        this.tradeTarget = tradeTarget;
    }
    // 거래일시 : created_at
}
