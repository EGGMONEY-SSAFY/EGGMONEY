package com.ssafy.eggmoney.savings.entity;

import com.ssafy.eggmoney.common.entity.BaseTime;
import com.ssafy.eggmoney.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
    private LocalDateTime paymentDate;
    private int paymentMoney;
    private int balance;
}
