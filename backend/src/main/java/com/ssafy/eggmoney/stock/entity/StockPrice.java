package com.ssafy.eggmoney.stock.entity;

import com.ssafy.eggmoney.common.entity.BaseTime;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Table(name = "stock_prices")
@NoArgsConstructor(access = PROTECTED)
public class StockPrice extends BaseTime {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "stock_price_id")
    private Long id;

    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "stock_id")
    private Stock stock;

    @NotNull
    private int price;

//    @NotNull
//    private LocalDateTime date;

//    public StockPrice(Stock stock, BigDecimal price, LocalDate date) {
//        this.stock = stock;
//        this.price = price.setScale(0, RoundingMode.HALF_UP).intValue();
//        this.date = date.atTime(16, 50, 0);
//    }

    public StockPrice(Stock stock, int price) {
        this.stock = stock;
        this.price = price;
    }
}
