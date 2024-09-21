package com.ssafy.eggmoney.stock.entity;

import com.ssafy.eggmoney.common.entity.BaseTime;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Table(name = "stocks")
@NoArgsConstructor(access = PROTECTED)
public class Stock extends BaseTime {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "stock_id")
    private Long id;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    private StockItem stockItem;

    @NotNull
    private int stockPrice;

    @NotNull
    private LocalDate date;

    public Stock(StockItem stockItem, BigDecimal stockPrice, LocalDate date) {
        this.stockItem = stockItem;
        this.stockPrice = stockPrice.setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
        this.date = date;
    }
}
