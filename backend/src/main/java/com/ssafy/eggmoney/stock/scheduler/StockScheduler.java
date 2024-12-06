package com.ssafy.eggmoney.stock.scheduler;

import com.ssafy.eggmoney.stock.entity.Stock;
import com.ssafy.eggmoney.stock.entity.StockPrice;
import com.ssafy.eggmoney.stock.repository.StockRepository;
import com.ssafy.eggmoney.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class StockScheduler {
    private final StockService stockService;
    private final StockRepository stockRepository;
    String[] stockCodes = {"0001", "1001", "4002", "4003", "4004", "4005", "4007",
            "4008", "4011", "4016", "4063", "4064", "4065"};

    @Scheduled(cron = "0 50 16 * * MON-FRI", zone = "Asia/Seoul")
    @Transactional
    public void saveDailyStockPrice() {
        log.info("주식 스케쥴링 시작: " + LocalDateTime.now());
        List<StockPrice> stockPrices = new ArrayList<>();

        try {
            String token = stockService.getAccessToken().getAccessToken();
            List<Stock> stocks = stockRepository.findAll();

            if(stocks.size() != stockCodes.length) {
                throw new IllegalStateException("조회된 지수의 개수가 잘못됐습니다.");
            }

            for(int i = 0; i < stockCodes.length; i++) {
                int currentStockPrice = stockService.getCurrentStockPrice(token, stockCodes[i])
                        .setScale(0, RoundingMode.HALF_UP).intValue();

                Stock stock = stocks.get(i);
                stock.changeCurrentPrice(currentStockPrice);

                StockPrice stockPrice = new StockPrice(stock, currentStockPrice);
                stockPrices.add(stockPrice);
            }

            stockService.saveCurrentStockPrices(stockPrices);
        } catch (Exception e) {
            log.error("현재 지수 업데이트 에러 발생: ", e);
        }
    }
}
