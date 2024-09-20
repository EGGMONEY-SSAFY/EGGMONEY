package com.ssafy.eggmoney.stock.entity;

import com.ssafy.eggmoney.common.entity.BaseTime;
import jakarta.persistence.*;
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

    @Enumerated(value = EnumType.STRING)
    private StockItem stockItem;

    private int price;
    private LocalDate date;

    public Stock(StockItem stockItem, BigDecimal price, LocalDate date) {
        this.stockItem = stockItem;
        this.price = price.setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
        this.date = date;
    }
}
