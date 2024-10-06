package com.ssafy.eggmoney.stock.entity;

import com.ssafy.eggmoney.common.entity.BaseTime;
import com.ssafy.eggmoney.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
public class StockPending extends BaseTime {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "stock_pending_id")
    private Long id;

    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "stock_id")
    private Stock stock;

    @NotNull
    @Enumerated(value = STRING)
    private TradeType tradeType;

    @NotNull
    private int pendingPrice;

    @NotNull
    private int pendingAmount;

    public StockPending(User user, Stock stock, TradeType tradeType, int pendingPrice, int pendingAmount) {
        this.user = user;
        this.stock = stock;
        this.tradeType = tradeType;
        this.pendingPrice = pendingPrice;
        this.pendingAmount = pendingAmount;
    }
}
