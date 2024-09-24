package com.ssafy.eggmoney.stock.service;

import com.ssafy.eggmoney.stock.dto.response.StockPriceResponse;
import com.ssafy.eggmoney.stock.dto.response.StockTokenResponse;
import com.ssafy.eggmoney.stock.entity.StockItem;

import java.math.BigDecimal;
import java.util.List;

public interface StockService {
    StockTokenResponse getAccessToken();
    List<StockPriceResponse> getStockPrices(String token, String inputDate, String stockCode);
    BigDecimal getCurrentStockPrice(String token, String stockCode);
    void saveStockPrices(List<StockPriceResponse> stockPrices, StockItem stockItem);
    void saveCurrentStockPrice(StockItem stockItem, BigDecimal currentStockPrice);
}
