package com.ssafy.eggmoney.stock.dto.response;

import com.ssafy.eggmoney.stock.entity.StockItem;
import com.ssafy.eggmoney.stock.entity.TradeType;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class StockLogResponse {
    private StockItem stockItem;
    private LocalDateTime tradeDate;
    private TradeType tradeType;
    private int price;
    private int amount;
    private int totalPrice;

    public StockLogResponse(StockItem stockItem, LocalDateTime tradeDate, TradeType tradeType, int price, int amount) {
        this.stockItem = stockItem;
        this.tradeDate = tradeDate;
        this.tradeType = tradeType;
        this.price = price;
        this.amount = amount;
        this.totalPrice = price * amount;
    }
}
