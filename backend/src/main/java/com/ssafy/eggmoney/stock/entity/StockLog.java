package com.ssafy.eggmoney.stock.entity;

import com.ssafy.eggmoney.common.entity.BaseTime;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Table(name = "stock_logs")
@NoArgsConstructor(access = PROTECTED)
public class StockLog extends BaseTime {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "stock_log_id")
    private Long id;

    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "stock_user_id")
    private StockUser stockUser;

    @NotNull
    @Enumerated(value = STRING)
    private TradeType tradeType;

    @NotNull
    private int tradePrice;

    @NotNull
    private int tradeAmount;

    public StockLog(StockUser stockUser, TradeType tradeType, int tradePrice, int tradeAmount) {
        this.stockUser = stockUser;
        this.tradeType = tradeType;
        this.tradePrice = tradePrice;
        this.tradeAmount = tradeAmount;
    }
}
