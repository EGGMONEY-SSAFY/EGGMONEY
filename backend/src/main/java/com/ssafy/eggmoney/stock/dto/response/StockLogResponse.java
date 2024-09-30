package com.ssafy.eggmoney.stock.dto.response;

import com.ssafy.eggmoney.stock.entity.StockItem;
import com.ssafy.eggmoney.stock.entity.TradeType;
import lombok.Getter;

@Getter
public class StockLogResponse {
    private StockItem stockItem;
    private TradeType tradeType;
    private int price;
    private int amount;
    private int totalPrice;

    public StockLogResponse(StockItem stockItem, TradeType tradeType, int price, int amount) {
        this.stockItem = stockItem;
        this.tradeType = tradeType;
        this.price = price;
        this.amount = amount;
        this.totalPrice = price * amount;
    }
}
