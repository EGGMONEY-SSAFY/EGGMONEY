package com.ssafy.eggmoney.stock.repository;

import com.ssafy.eggmoney.stock.dto.response.StockPriceForYearResponse;
import com.ssafy.eggmoney.stock.entity.StockItem;

import java.util.List;

public interface StockCostomRepository {
    List<StockPriceForYearResponse> findStockPricesForYear(StockItem stockItem);
}
