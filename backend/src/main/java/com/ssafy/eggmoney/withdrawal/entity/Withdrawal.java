package com.ssafy.eggmoney.withdrawal.entity;

import com.ssafy.eggmoney.common.entity.BaseTime;
import com.ssafy.eggmoney.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "withdrawals")
@Getter
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

    private WithdrawalType withdrawalStatus;

    @Builder
    private Withdrawal(User user, WithdrawalType withdrawalStatus, int withdrawalPrice) {
        this.user = user;
        this.withdrawalPrice = withdrawalPrice;
        this.withdrawalStatus = withdrawalStatus;
    }

    public void setWithdrawalStatus(WithdrawalType withdrawalStatus) {
        this.withdrawalStatus = withdrawalStatus;
    }

}
