package com.ssafy.eggmoney.account.entity;

import com.ssafy.eggmoney.common.entity.BaseTime;
import com.ssafy.eggmoney.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Table(name = "accounts")
@NoArgsConstructor(access = PROTECTED)
public class Account extends BaseTime {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "account_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Integer balance;

    @Builder
    private Account(User user, Integer balance) {
        this.user = user;
        this.balance = balance;
    }

}
