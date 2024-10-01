package com.ssafy.eggmoney.stock.service;

import com.ssafy.eggmoney.stock.dto.response.StockLogResponse;
import com.ssafy.eggmoney.stock.entity.StockUser;
import com.ssafy.eggmoney.stock.entity.TradeType;

import java.util.List;

public interface StockLogService {
    void saveStockLog(StockUser stockUser, TradeType tradeType, int price, int amount);
    List<StockLogResponse> findStockLogByUserId(Long userId);
}
