package com.ssafy.eggmoney.stock.dto.response;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class StockPriceForYearResponse {
    private LocalDate date;
    private int price;

    public StockPriceForYearResponse(LocalDateTime date, int price) {
        this.date = date.toLocalDate();
        this.price = price;
    }
}
