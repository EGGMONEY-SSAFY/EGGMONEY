package com.ssafy.eggmoney.withdrawal.entity;

import com.ssafy.eggmoney.common.entity.BaseTime;
import com.ssafy.eggmoney.user.entity.User;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "withdrawals")
@NoArgsConstructor(access = PROTECTED)
public class Withdrawal extends BaseTime {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "withdrawal_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private int withdrawalPrice;
    private String withdrawalStatus;


}
