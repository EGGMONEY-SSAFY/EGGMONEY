package com.ssafy.eggmoney.stock.entity;

import com.ssafy.eggmoney.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Table(name = "stock_pendings")
@NoArgsConstructor(access = PROTECTED)
public class StockPending {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "stock_pending_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "stock_id")
    private Stock stock;

    @Enumerated(value = STRING)
    private TradeType tradeType;

    private int pendingPrice;
    private int pendingAmount;
}
