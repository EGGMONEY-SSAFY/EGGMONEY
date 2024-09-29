//package com.ssafy.eggmoney.stock.service;
//
//import com.ssafy.eggmoney.stock.dto.api.StockPriceDto;
//import com.ssafy.eggmoney.stock.dto.api.StockTokenDto;
//import com.ssafy.eggmoney.stock.entity.Stock;
//import com.ssafy.eggmoney.stock.entity.StockItem;
//import com.ssafy.eggmoney.stock.repository.StockRepository;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.nio.file.attribute.BasicFileAttributes;
//import java.time.Instant;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.temporal.ChronoUnit;
//import java.util.ArrayList;
//import java.util.List;
//
//@SpringBootTest
//@Transactional
//@Rollback(value = false)
//class StockServiceImplTest {
//    @Autowired StockService stockService;
//    @Autowired StockRepository stockRepository;
//    String[] stockCodes = {"0001", "1001", "4002", "4003", "4004", "4005", "4007",
//            "4008", "4011", "4016", "4063", "4064", "4065"};
//    StockItem[] stockItems = {StockItem.KOSPI, StockItem.KOSDAQ, StockItem.AUTOMOTIVE,
//            StockItem.SEMICONDUCTOR, StockItem.HEALTHCARE, StockItem.BANKING, StockItem.ENERGY_CHEMICAL,
//            StockItem.STEEL, StockItem.CONSTRUCTION, StockItem.TRANSPORTATION, StockItem.MEDIA_ENTERTAINMENT,
//            StockItem.IT, StockItem.UTILITIES};
//
//    @Test
//    void getToken() {
//        StockTokenDto stockToken = stockService.getAccessToken();
//
//        Assertions.assertThat(stockToken.getAccessToken()).isNotNull();
//        Assertions.assertThat(stockToken.getExpiresIn()).isEqualTo(86400);
//
//        Path path = Paths.get("C:/Temp/stock_token.txt");
//        try {
//            Files.write(path, stockToken.getAccessToken().getBytes());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test
//    void getStockPrices() {
//        String token;
//        Path path = Paths.get("C:/Temp/stock_token.txt");
//        try {
//            BasicFileAttributes basicFileAttributes = Files.readAttributes(path, BasicFileAttributes.class);
//            Instant lastModifiedTime = basicFileAttributes.lastModifiedTime().toInstant();
//            Instant now = Instant.now();
//
//            if(lastModifiedTime.isBefore(now.minus(24, ChronoUnit.HOURS))) {
//                getToken();
//            }
//
//            token = String.join("\n", Files.readAllLines(path));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
////        List<StockPriceDto> stockPrices = stockService.getStockPrices(token, "20240921", "0001");
//
////        Assertions.assertThat(stockPrices).hasSize(100);
//    }
//
//    @Test
//    public void saveStockPrices() {
//        String token;
//        Path path = Paths.get("C:/Temp/stock_token.txt");
//        try {
//            BasicFileAttributes basicFileAttributes = Files.readAttributes(path, BasicFileAttributes.class);
//            Instant lastModifiedTime = basicFileAttributes.lastModifiedTime().toInstant();
//            Instant now = Instant.now();
//
//            if(lastModifiedTime.isBefore(now.minus(24, ChronoUnit.HOURS))) {
//                getToken();
//            }
//
//            token = String.join("\n", Files.readAllLines(path));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        for (int i = 0; i < stockCodes.length; i++) {
////            List<StockPriceDto> stockPrices = stockService.getStockPrices(token, "20231130", stockCodes[i]);
////            stockService.saveStockPrices(stockPrices, stockItems[i]);
//        }
//    }
//
//    @Test
//    void getCurrentStockPrice() {
//        String token;
//        Path path = Paths.get("C:/Temp/stock_token.txt");
//        try {
//            BasicFileAttributes basicFileAttributes = Files.readAttributes(path, BasicFileAttributes.class);
//            Instant lastModifiedTime = basicFileAttributes.lastModifiedTime().toInstant();
//            Instant now = Instant.now();
//
//            if(lastModifiedTime.isBefore(now.minus(24, ChronoUnit.HOURS))) {
//                getToken();
//            }
//
//            token = String.join("\n", Files.readAllLines(path));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        BigDecimal currentStockPrice = stockService.getCurrentStockPrice(token, stockCodes[0]);
//
//        Assertions.assertThat(currentStockPrice).isNotNull();
//    }
//
//    @Test
//    void saveCurrentStockPrice() {
//        String token;
//        Path path = Paths.get("C:/Temp/stock_token.txt");
//        try {
//            BasicFileAttributes basicFileAttributes = Files.readAttributes(path, BasicFileAttributes.class);
//            Instant lastModifiedTime = basicFileAttributes.lastModifiedTime().toInstant();
//            Instant now = Instant.now();
//
//            if(lastModifiedTime.isBefore(now.minus(24, ChronoUnit.HOURS))) {
//                getToken();
//            }
//
//            token = String.join("\n", Files.readAllLines(path));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        List<Stock> stocks = new ArrayList<>();
//        for(int i = 0; i < stockCodes.length; i++) {
//            BigDecimal currentStockPrice = stockService.getCurrentStockPrice(token, stockCodes[i]);
//            Stock stock = new Stock(stockItems[i], currentStockPrice);
//            stocks.add(stock);
//        }
//
//        stockService.saveCurrentStockPrices(stocks);
//    }
//
//    @Test
//    void getLatestDateStocks() {
//        List<StockItem> stockItems = stockRepository.findStockItems();
//        List<Integer> top2LatestPrices = stockRepository.findTop2LatestPrices(StockItem.KOSPI);
//
//        Assertions.assertThat(stockItems).hasSize(13);
//        Assertions.assertThat(top2LatestPrices).hasSize(2);
//    }
//
//}