package com.ssafy.eggmoney.stock.service;

import com.ssafy.eggmoney.stock.dto.request.StockBuyRequest;
import com.ssafy.eggmoney.stock.dto.request.StockSellRequest;
import com.ssafy.eggmoney.stock.dto.response.StockBuyResponse;
import com.ssafy.eggmoney.stock.dto.response.StockSellResponse;

import java.util.Map;

public interface StockUserService {
    Map<String, Object> findInvestablePrice(Long userId);
    StockBuyResponse buyStock(StockBuyRequest stockBuy);
    StockSellResponse sellStock(StockSellRequest stockSellReq);
}
