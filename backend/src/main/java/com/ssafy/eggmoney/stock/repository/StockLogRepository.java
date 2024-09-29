package com.ssafy.eggmoney.stock.repository;

import com.ssafy.eggmoney.stock.entity.StockLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockLogRepository extends JpaRepository<StockLog, Long> {
}
