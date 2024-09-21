package com.ssafy.eggmoney.stock.service;

import com.ssafy.eggmoney.stock.dto.response.StockPriceResponse;
import com.ssafy.eggmoney.stock.dto.response.StockTokenResponse;

import java.util.List;

public interface StockService {
    StockTokenResponse getAccessToken();
    List<StockPriceResponse> getStockPrices(String token, String inputDate, String stockCode);
    void saveStockPrices(String token, String inputDate, String stockCode);
}
