package com.ssafy.eggmoney.stock.repository;

import com.ssafy.eggmoney.stock.entity.StockUser;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StockUserRepository extends JpaRepository<StockUser, Long> {
    Optional<StockUser> findByStockIdAndUserId(Long stockId, Long userId);
    List<StockUser> findByUserId(Long userId);

    @EntityGraph(attributePaths = {"stock"})
    List<StockUser> findJoinByUserId(Long userId);
}
