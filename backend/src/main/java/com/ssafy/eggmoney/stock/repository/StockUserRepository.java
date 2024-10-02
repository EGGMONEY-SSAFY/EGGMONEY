package com.ssafy.eggmoney.stock.repository;

import com.ssafy.eggmoney.stock.entity.StockItem;
import com.ssafy.eggmoney.stock.entity.StockUser;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StockUserRepository extends JpaRepository<StockUser, Long> {
    Optional<StockUser> findByStockIdAndUserId(Long stockId, Long userId);
    List<StockUser> findByUserId(Long userId);

    @EntityGraph(attributePaths = {"stock"})
    List<StockUser> findJoinStockByUserId(Long userId);

    @Query("select su from StockUser su left join fetch su.stock s " +
            "where su.user.id = :userId and s.stockItem = :item")
    Optional<StockUser> findByUserIdAndStockItem(@Param("userId") Long userId, @Param("item") StockItem item);
}
