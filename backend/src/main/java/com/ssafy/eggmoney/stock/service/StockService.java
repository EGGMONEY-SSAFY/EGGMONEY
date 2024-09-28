package com.ssafy.eggmoney.stock.service;

import com.ssafy.eggmoney.stock.dto.api.StockPriceDto;
import com.ssafy.eggmoney.stock.dto.api.StockTokenDto;
import com.ssafy.eggmoney.stock.dto.response.StockPriceForYearResponse;
import com.ssafy.eggmoney.stock.dto.response.StockPriceResponse;
import com.ssafy.eggmoney.stock.entity.Stock;
import com.ssafy.eggmoney.stock.entity.StockItem;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface StockService {
    StockTokenDto getAccessToken();
    BigDecimal getCurrentStockPrice(String token, String stockCode);
    void saveCurrentStockPrices(List<Stock> stocks);
    List<StockPriceResponse> findLatestStockPrice();
    LocalDateTime findLatestDate();
    List<StockPriceForYearResponse> findStockPricesForYear(StockItem stockItem);
}
