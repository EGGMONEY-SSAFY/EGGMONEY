package com.ssafy.eggmoney.stock.scheduler;

import com.ssafy.eggmoney.stock.entity.Stock;
import com.ssafy.eggmoney.stock.entity.StockItem;
import com.ssafy.eggmoney.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class StockScheduler {
    private final StockService stockService;
    String[] stockCodes = {"0001", "1001", "4002", "4003", "4004", "4005", "4007",
            "4008", "4011", "4016", "4063", "4064", "4065"};
    StockItem[] stockItems = {StockItem.KOSPI, StockItem.KOSDAQ, StockItem.AUTOMOTIVE,
            StockItem.SEMICONDUCTOR, StockItem.HEALTHCARE, StockItem.BANKING, StockItem.ENERGY_CHEMICAL,
            StockItem.STEEL, StockItem.CONSTRUCTION, StockItem.TRANSPORTATION, StockItem.MEDIA_ENTERTAINMENT,
            StockItem.IT, StockItem.UTILITIES};

//    @Scheduled(cron = "0 50 16 * * MON-FRI", zone = "Asia/Seoul")
    public void saveDailyStockPrice() {
        log.info("주식 스케쥴링 시작: " + LocalDateTime.now());
        List<Stock> stocks = new ArrayList<>();
        String token = stockService.getAccessToken().getAccessToken();

        if(token == null) {
            log.warn("주식 토큰 요청이 실패했습니다.");
        }

        try {
            for(int i = 0; i < stockCodes.length; i++) {
                BigDecimal currentStockPrice = stockService.getCurrentStockPrice(token, stockCodes[i]);
                Stock stock = new Stock(stockItems[i], currentStockPrice);
                stocks.add(stock);
            }
        } catch (Exception e) {
            log.error("주식 api 요청 에러 발생: ", e);
        }

        stockService.saveCurrentStockPrices(stocks);
    }
}
