package com.ssafy.eggmoney.stock.dto.request;

import lombok.Getter;

@Getter
public class StockBuyRequest {
    private Long userId;
    private Long stockId;
    private int amount;
}
