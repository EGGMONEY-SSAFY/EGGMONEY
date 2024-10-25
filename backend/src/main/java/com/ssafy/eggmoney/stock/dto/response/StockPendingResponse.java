package com.ssafy.eggmoney.stock.dto.response;

import com.ssafy.eggmoney.stock.entity.TradeType;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class StockPendingResponse {
    Long stockId;
    Long stockPendingId;
    TradeType tradeType;
    int pendingPrice;
    int pendingAmount;
    int totalPrice;
    LocalDateTime orderDate;

    public StockPendingResponse(Long stockId, Long stockPendingId, TradeType tradeType,
                                int pendingPrice, int pendingAmount, LocalDateTime orderDate) {
        this.stockId = stockId;
        this.stockPendingId = stockPendingId;
        this.tradeType = tradeType;
        this.pendingPrice = pendingPrice;
        this.pendingAmount = pendingAmount;
        this.totalPrice = pendingAmount * pendingPrice;
        this.orderDate = orderDate;
    }
}
