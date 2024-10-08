package com.ssafy.eggmoney.stock.repository;

import com.ssafy.eggmoney.stock.entity.StockPending;
import com.ssafy.eggmoney.stock.entity.TradeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StockPendingRepository extends JpaRepository<StockPending, Long> {
    List<StockPending> findByUserIdAndTradeType(Long userId, TradeType tradeType);
    List<StockPending> findByUserIdOrderByCreatedAtDesc(Long userId);
    List<StockPending> findByUserIdAndStockIdAndTradeType(Long userId, Long stockId, TradeType tradeType);

    @Query("select sp from StockPending sp join fetch sp.stock s " +
            "where sp.tradeType = 'SELL' and s.currentPrice >= sp.pendingPrice")
    List<StockPending> findPendingSell();
}
