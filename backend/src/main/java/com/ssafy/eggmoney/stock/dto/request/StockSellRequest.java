package com.ssafy.eggmoney.stock.dto.request;

import lombok.Getter;

@Getter
public class StockSellRequest {
    private Long userId;
    private Long stockId;
    private int amount;
}
