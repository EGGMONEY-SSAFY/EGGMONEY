package com.ssafy.eggmoney.stock.repository;

import com.ssafy.eggmoney.stock.entity.StockPrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockPriceRepository extends JpaRepository<StockPrice, Long> {
}
