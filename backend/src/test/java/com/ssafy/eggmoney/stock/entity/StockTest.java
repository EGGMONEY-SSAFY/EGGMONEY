//package com.ssafy.eggmoney.stock.entity;
//
//import com.ssafy.eggmoney.stock.repository.StockRepository;
//import jakarta.persistence.EntityManager;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@Transactional
//class StockTest {
//    @Autowired
//    StockRepository stockRepository;
//    @Autowired
//    EntityManager em;
//
//    StockItem[] stockItems = {StockItem.KOSPI, StockItem.KOSDAQ, StockItem.AUTOMOTIVE,
//            StockItem.SEMICONDUCTOR, StockItem.HEALTHCARE, StockItem.BANKING, StockItem.ENERGY_CHEMICAL,
//            StockItem.STEEL, StockItem.CONSTRUCTION, StockItem.TRANSPORTATION, StockItem.MEDIA_ENTERTAINMENT,
//            StockItem.IT, StockItem.UTILITIES};
//
//    @Test
//    public void saveAll() {
//        long beforeCount = stockRepository.count();
//
//        List<Stock> stocks = new ArrayList<>();
//        for (StockItem stockItem : stockItems) {
//            Stock stock = new Stock(stockItem);
//            stocks.add(stock);
//        }
//        stockRepository.saveAll(stocks);
//
//        em.flush();
//        em.clear();
//
//        long count = stockRepository.count();
//
//        Assertions.assertThat(count - beforeCount).isEqualTo(stockItems.length);
//    }
//}