package com.ssafy.eggmoney.stock.service;

import com.ssafy.eggmoney.stock.dto.api.StockPriceDto;
import com.ssafy.eggmoney.stock.dto.api.StockTokenDto;
import com.ssafy.eggmoney.stock.dto.response.StockPriceResponse;
import com.ssafy.eggmoney.stock.entity.Stock;
import com.ssafy.eggmoney.stock.entity.StockItem;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface StockService {
    StockTokenDto getAccessToken();
    List<StockPriceDto> getStockPrices(String token, String inputDate, String stockCode);
    BigDecimal getCurrentStockPrice(String token, String stockCode);
    void saveStockPrices(List<StockPriceDto> stockPrices, StockItem stockItem);
    void saveCurrentStockPrices(List<Stock> stocks);
    List<StockPriceResponse> findLatestStockPrice();
    LocalDate findLatestDate();
}
