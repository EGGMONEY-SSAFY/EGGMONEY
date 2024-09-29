package com.ssafy.eggmoney.stock.dto.response;

import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
public class StockSellResponse {
    private int buyAverage;
    private int amount;
    private int totalInvestment;
    private int value;
    private int roi;

    public StockSellResponse() {}

    public StockSellResponse(int buyAverage, int amount, int price) {
        this.buyAverage = buyAverage;
        this.amount = amount;
        this.totalInvestment = buyAverage * amount;
        this.value = price * amount;
        this.roi = BigDecimal.valueOf(value - totalInvestment)
                .divide(BigDecimal.valueOf(totalInvestment), 2, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100)).intValue();
    }
}
