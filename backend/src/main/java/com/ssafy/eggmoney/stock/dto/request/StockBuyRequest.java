package com.ssafy.eggmoney.stock.dto.request;

import com.ssafy.eggmoney.stock.entity.StockItem;
import lombok.Getter;

@Getter
public class StockBuyRequest {
    private Long userId;
    private StockItem stockItem;
    private int amount;
}