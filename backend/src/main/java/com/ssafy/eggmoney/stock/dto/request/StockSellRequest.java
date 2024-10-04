package com.ssafy.eggmoney.stock.dto.request;

import lombok.Getter;

@Getter
public class StockSellRequest {
    private Long stockId;
    private int amount;
}
