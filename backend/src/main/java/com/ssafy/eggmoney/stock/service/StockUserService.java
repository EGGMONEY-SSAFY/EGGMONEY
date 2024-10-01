package com.ssafy.eggmoney.stock.service;

import com.ssafy.eggmoney.stock.dto.request.StockBuyRequest;
import com.ssafy.eggmoney.stock.dto.request.StockSellRequest;
import com.ssafy.eggmoney.stock.dto.request.StockUserRequest;
import com.ssafy.eggmoney.stock.dto.response.StockUserResponse;

import java.util.Map;

public interface StockUserService {
    Map<String, Object> findInvestablePrice(Long userId);
    Map<Object, Integer> findUserStocks(Long userId);
    StockUserResponse buyStock(StockBuyRequest stockBuy);
    StockUserResponse sellStock(StockSellRequest stockSellReq);
    StockUserResponse findStockUserInfo(StockUserRequest stockUserReq);
}
