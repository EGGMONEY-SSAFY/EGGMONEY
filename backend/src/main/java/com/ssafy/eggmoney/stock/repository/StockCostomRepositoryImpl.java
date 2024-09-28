package com.ssafy.eggmoney.stock.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.eggmoney.stock.dto.response.StockPriceForYearResponse;
import com.ssafy.eggmoney.stock.entity.StockItem;
import jakarta.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import static com.ssafy.eggmoney.stock.entity.QStock.stock;

public class StockCostomRepositoryImpl implements StockCostomRepository {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public StockCostomRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Integer> findTop2LatestPrices(StockItem stockItem) {
        List<Integer> prices = queryFactory
                .select(stock.stockPrice)
                .from(stock)
                .where(stock.stockItem.eq(stockItem))
                .orderBy(stock.createdAt.desc())
                .offset(0)
                .limit(2)
                .fetch();

        if (prices.size() < 2) {
            throw new NoSuchElementException("최신 날짜의 가격이 조회되지 않습니다.");
        }

        return prices;
    }

    @Override
    public LocalDateTime findLatestDate() {
        LocalDateTime date = queryFactory
                .select(stock.createdAt)
                .from(stock)
                .orderBy(stock.createdAt.desc())
                .offset(0)
                .limit(1)
                .fetchOne();

        if (date == null) {
            throw new NoSuchElementException("최신 날짜 조회를 할 수 없습니다.");
        }

        return date;
    }

    @Override
    public List<StockPriceForYearResponse> findStockPricesForYear(StockItem stockItem) {
        List<StockPriceForYearResponse> stockPricesForYear = queryFactory
                .select(Projections.constructor(StockPriceForYearResponse.class,
                        stock.createdAt, stock.stockPrice))
                .from(stock)
                .where(stock.stockItem.eq(stockItem)
                        .and(stock.createdAt.between(LocalDateTime.now().minusYears(1), LocalDateTime.now())))
                .orderBy(stock.createdAt.desc())
                .fetch();

        if (stockPricesForYear.isEmpty()) {
            throw new NoSuchElementException("1년치 주식 데이터가 조회되지 않습니다.");
        }

        return stockPricesForYear;
    }
}
