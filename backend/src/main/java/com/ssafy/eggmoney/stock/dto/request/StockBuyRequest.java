package com.ssafy.eggmoney.stock.dto.request;

import lombok.Getter;

@Getter
public class StockBuyRequest {
    private Long stockId;
    private int amount;
}
