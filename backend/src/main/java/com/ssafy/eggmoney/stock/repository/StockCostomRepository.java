package com.ssafy.eggmoney.stock.repository;

import com.ssafy.eggmoney.stock.dto.response.StockPriceForYearResponse;
import com.ssafy.eggmoney.stock.entity.StockItem;

import java.time.LocalDateTime;
import java.util.List;

public interface StockCostomRepository {
    List<Integer> findTop2LatestPrices(StockItem stockItem);
    LocalDateTime findLatestDate();
    List<StockPriceForYearResponse> findStockPricesForYear(StockItem stockItem);
}
