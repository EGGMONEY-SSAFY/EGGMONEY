package com.ssafy.eggmoney.stock.service;

import com.ssafy.eggmoney.stock.dto.request.PendingTradeRequest;
import com.ssafy.eggmoney.stock.dto.response.StockPendingResponse;
import com.ssafy.eggmoney.stock.entity.TradeType;

import java.util.List;

public interface StockPendingService {
    void saveStockPending(PendingTradeRequest pendingReq, TradeType type, Long userId);
    int findPendingBuyTotalPrice(Long userId);
    int findIndividualPendingAmount(Long userId, Long stockId, TradeType tradeType);
    int findIndividualPendingPrice(Long userId, Long stockId, TradeType tradeType);
    List<StockPendingResponse> findPendingLog(Long userId);
    void deleteStockPending(Long stockPendingId,Long userId);
}
