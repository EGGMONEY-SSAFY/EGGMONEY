package com.ssafy.eggmoney.savings.entity;

import com.ssafy.eggmoney.common.entity.BaseTime;
import com.ssafy.eggmoney.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Table(name = "savings")
@NoArgsConstructor(access = PROTECTED)
public class Savings extends BaseTime {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "savings_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "savings_product_id")
    private SavingsProduct savingsProduct;

    private LocalDateTime expireDate;
    private int paymentDate; // 남은 납입 횟수
    private int paymentMoney; // 정액 적금 금액
    private int balance; // 총 납부금

    @Enumerated(value = EnumType.STRING)
    private SavingsStatus savingsStatus; // AVAILABLE, EXPIRED

    @Builder(toBuilder = true)
    public Savings(Long id, User user, SavingsProduct savingsProduct, LocalDateTime expireDate, int paymentDate, int paymentMoney, int balance, SavingsStatus savingsStatus) {
        this.id = id;
        this.user = user;
        this.savingsProduct = savingsProduct;
        this.expireDate = expireDate;
        this.paymentDate = paymentDate;
        this.paymentMoney = paymentMoney;
        this.balance = balance;
        this.savingsStatus = savingsStatus;

    }

}
