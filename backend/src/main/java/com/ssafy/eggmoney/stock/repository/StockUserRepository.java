package com.ssafy.eggmoney.stock.repository;

import com.ssafy.eggmoney.stock.entity.StockUser;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StockUserRepository extends JpaRepository<StockUser, Long> {
    @EntityGraph(attributePaths = {"stock"})
    List<StockUser> findJoinStockByUserId(Long userId);

    @EntityGraph(attributePaths = {"stock"})
    Optional<StockUser> findJoinStockByUserIdAndStockId(Long userId, Long stockId);

    Optional<StockUser> findByUserIdAndStockId(Long userId, Long stockId);
}
