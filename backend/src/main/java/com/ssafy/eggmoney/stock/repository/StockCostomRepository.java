package com.ssafy.eggmoney.stock.repository;

import com.ssafy.eggmoney.stock.entity.StockItem;

import java.time.LocalDate;
import java.util.List;

public interface StockCostomRepository {
    List<Integer> findTop2LatestPrices(StockItem stockItem);
    LocalDate findLatestDate();
}
