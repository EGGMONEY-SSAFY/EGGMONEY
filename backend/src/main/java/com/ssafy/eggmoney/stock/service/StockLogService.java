package com.ssafy.eggmoney.stock.service;

import com.ssafy.eggmoney.stock.entity.StockUser;
import com.ssafy.eggmoney.stock.entity.TradeType;

public interface StockLogService {
    void saveStockLog(StockUser stockUser, TradeType tradeType, int price, int amount);
}
