package com.ssafy.eggmoney.stock.entity;

import com.ssafy.eggmoney.common.entity.BaseTime;
import jakarta.persistence.*;
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

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "stock_user_id")
    private StockUser stockUser;

    @Enumerated(value = STRING)
    private TradeType tradeType;

    private boolean isExecution;
    private int tradeAmount;
    private int price;
}
