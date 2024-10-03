package com.ssafy.eggmoney.stock.repository;

import com.ssafy.eggmoney.stock.entity.StockLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StockLogRepository extends JpaRepository<StockLog, Long> {
    @Query("select sl from StockLog sl left join fetch sl.stockUser su " +
            "where su.user.id = :userId order by sl.createdAt desc")
    List<StockLog> findByUserId(@Param("userId") Long userId);
}
