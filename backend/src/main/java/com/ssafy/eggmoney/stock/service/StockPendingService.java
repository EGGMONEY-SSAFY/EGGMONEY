package com.ssafy.eggmoney.stock.service;

import com.ssafy.eggmoney.stock.dto.request.PendingRequest;
import com.ssafy.eggmoney.stock.entity.TradeType;

public interface StockPendingService {
    void saveStockPending(PendingRequest pendingReq, TradeType type);
    int findPendingBuyTotalPrice(Long userId);
}
