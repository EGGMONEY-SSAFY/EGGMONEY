package com.ssafy.eggmoney.stock.repository;

import com.ssafy.eggmoney.stock.entity.Stock;
import com.ssafy.eggmoney.stock.entity.StockItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long>, StockCostomRepository {
    Optional<Stock> findByStockItemAndCreatedAt(StockItem stockItem, LocalDateTime date);
}
