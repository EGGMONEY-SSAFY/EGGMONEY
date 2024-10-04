package com.ssafy.eggmoney.stock.service;

import com.ssafy.eggmoney.stock.dto.request.PendingTradeRequest;
import com.ssafy.eggmoney.stock.dto.response.StockPendingResponse;
import com.ssafy.eggmoney.stock.entity.TradeType;

import java.util.List;

public interface StockPendingService {
    void saveStockPending(PendingTradeRequest pendingReq, TradeType type, Long userId);
    int findPendingBuyTotalPrice(Long userId);
    int findPendingSellTotalAmount(Long userId);
    List<StockPendingResponse> findPendingLog(Long userId);
}
