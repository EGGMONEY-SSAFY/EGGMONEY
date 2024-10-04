package com.ssafy.eggmoney.stock.service;

import com.ssafy.eggmoney.stock.dto.api.StockPriceDto;
import com.ssafy.eggmoney.stock.dto.api.StockTokenDto;
import com.ssafy.eggmoney.stock.dto.response.StockPriceForYearResponse;
import com.ssafy.eggmoney.stock.dto.response.StockPriceResponse;
import com.ssafy.eggmoney.stock.entity.Stock;
import com.ssafy.eggmoney.stock.entity.StockPrice;
import com.ssafy.eggmoney.stock.entity.StockItem;

import java.math.BigDecimal;
import java.util.List;

public interface StockService {
    StockTokenDto getAccessToken();
    List<StockPriceDto> getStockPrices(String token, String inputDate, String stockCode);
    void saveStockPrices(Stock stock, List<StockPriceDto> stockPrices);
    BigDecimal getCurrentStockPrice(String token, String stockCode);
    void saveCurrentStockPrices(List<StockPrice> stockPrices);
    List<StockPriceResponse> findLatestStockPrices();
    List<StockPriceForYearResponse> findStockPricesForYear(Long stockId);
}
