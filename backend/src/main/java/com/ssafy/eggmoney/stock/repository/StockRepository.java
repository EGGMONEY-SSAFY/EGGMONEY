package com.ssafy.eggmoney.stock.repository;

import com.ssafy.eggmoney.stock.entity.Stock;
import com.ssafy.eggmoney.stock.entity.StockItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Long>, StockCostomRepository {
    List<Stock> findTop2ByStockItemOrderByUpdatedAtDesc(StockItem stockItem);
    List<Stock> findTop13ByOrderByUpdatedAtDesc();
}
