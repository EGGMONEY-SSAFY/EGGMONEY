package com.ssafy.eggmoney.savings.entity;

import com.ssafy.eggmoney.common.entity.BaseTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Table(name = "savings_logs")
@NoArgsConstructor(access = PROTECTED)
public class SavingsLog extends BaseTime {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "savings_log_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "savings_id" )
    private Savings savings;

    private int balance;
}
