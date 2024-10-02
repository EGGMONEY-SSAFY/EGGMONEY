package com.ssafy.eggmoney.stock.repository;

import com.ssafy.eggmoney.stock.entity.StockPrice;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StockPriceRepository extends JpaRepository<StockPrice, Long> {
    @Query("select sp from StockPrice sp left join fetch sp.stock s")
    List<StockPrice> findJoinStock(Pageable pageable);
}
