//package com.ssafy.eggmoney.stock.service;
//
//import com.ssafy.eggmoney.stock.dto.api.StockPriceDto;
//import com.ssafy.eggmoney.stock.dto.api.StockTokenDto;
//import com.ssafy.eggmoney.stock.entity.Stock;
//import com.ssafy.eggmoney.stock.entity.StockItem;
//import com.ssafy.eggmoney.stock.entity.StockPrice;
//import com.ssafy.eggmoney.stock.repository.StockPriceRepository;
//import com.ssafy.eggmoney.stock.repository.StockRepository;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.math.RoundingMode;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.nio.file.attribute.BasicFileAttributes;
//import java.time.Instant;
//import java.time.temporal.ChronoUnit;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.NoSuchElementException;
//
//@SpringBootTest
//@Transactional
//class StockServiceImplTest {
//    @Autowired StockService stockService;
//    @Autowired StockRepository stockRepository;
//    String[] stockCodes = {"0001", "1001", "4002", "4003", "4004", "4005", "4007",
//            "4008", "4011", "4016", "4063", "4064", "4065"};
//    StockItem[] stockItems = {StockItem.KOSPI, StockItem.KOSDAQ, StockItem.AUTOMOTIVE,
//            StockItem.SEMICONDUCTOR, StockItem.HEALTHCARE, StockItem.BANKING, StockItem.ENERGY_CHEMICAL,
//            StockItem.STEEL, StockItem.CONSTRUCTION, StockItem.TRANSPORTATION, StockItem.MEDIA_ENTERTAINMENT,
//            StockItem.IT, StockItem.UTILITIES};
//    @Autowired
//    private StockPriceRepository stockPriceRepository;
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
//    String getTokenFile() {
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
//        return token;
//    }
//
//    @Test
//    void getStockPrices() {
//        String token = getTokenFile();
//        List<StockPriceDto> stockPrices = stockService.getStockPrices(token, "20240921", "0001");
//
//        Assertions.assertThat(stockPrices).hasSize(100);
//    }
//
//    @Test
//    public void saveStockPrices() {
//        String token = getTokenFile();
//
//        for (int i = 0; i < stockCodes.length; i++) {
//            List<StockPriceDto> stockPrices = stockService.getStockPrices(token, "20231205", stockCodes[i]);
//            Stock stock = stockRepository.findByStockItem(stockItems[i])
//                    .orElseThrow(() -> new NoSuchElementException("주식 조회 실패"));
//            stockService.saveStockPrices(stock, stockPrices);
//        }
//    }
//
//    @Test
//    void getCurrentStockPrice() {
//        String token = getTokenFile();
//
//        BigDecimal currentStockPrice = stockService.getCurrentStockPrice(token, stockCodes[0]);
//
//        Assertions.assertThat(currentStockPrice).isNotNull();
//    }
//
//    @Test
//    void saveCurrentStockPrice() {
//        String token = getTokenFile();
//        List<StockPrice> stockPrices = new ArrayList<>();
//        long beforeCount = stockPriceRepository.count();
//
//        List<Stock> stocks = stockRepository.findAll();
//
//        if(stocks.size() != stockCodes.length) {
//            throw new IllegalStateException("조회된 지수의 개수가 잘못됐습니다.");
//        }
//
//        for(int i = 0; i < stockCodes.length; i++) {
//            int currentStockPrice = stockService.getCurrentStockPrice(token, stockCodes[i])
//                    .setScale(0, RoundingMode.HALF_UP).intValue();
//
//            Stock stock = stocks.get(i);
//            stock.changeCurrentPrice(currentStockPrice);
//
//            StockPrice stockPrice = new StockPrice(stock, currentStockPrice);
//            stockPrices.add(stockPrice);
//        }
//        stockService.saveCurrentStockPrices(stockPrices);
//
//        long count = stockPriceRepository.count();
//
//        Assertions.assertThat(count).isEqualTo(beforeCount + stockCodes.length);
//    }
//}