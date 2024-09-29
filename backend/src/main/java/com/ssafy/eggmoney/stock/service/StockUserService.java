package com.ssafy.eggmoney.stock.service;

import com.ssafy.eggmoney.stock.dto.request.StockBuyRequest;
import com.ssafy.eggmoney.stock.dto.response.StockUserResponse;

import java.util.Map;

public interface StockUserService {
    Map<String, Object> findInvestableRatio(Long userId);
    StockUserResponse buyStock(StockBuyRequest stockBuy);
}
