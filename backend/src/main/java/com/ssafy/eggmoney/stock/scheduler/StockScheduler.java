package com.ssafy.eggmoney.stock.scheduler;

import com.ssafy.eggmoney.stock.entity.Stock;
import com.ssafy.eggmoney.stock.entity.StockPrice;
import com.ssafy.eggmoney.stock.entity.StockItem;
import com.ssafy.eggmoney.stock.repository.StockRepository;
import com.ssafy.eggmoney.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
@Slf4j
public class StockScheduler {
    private final StockService stockService;
    private final StockRepository stockRepository;
    String[] stockCodes = {"0001", "1001", "4002", "4003", "4004", "4005", "4007",
            "4008", "4011", "4016", "4063", "4064", "4065"};
    StockItem[] stockItems = {StockItem.KOSPI, StockItem.KOSDAQ, StockItem.AUTOMOTIVE,
            StockItem.SEMICONDUCTOR, StockItem.HEALTHCARE, StockItem.BANKING, StockItem.ENERGY_CHEMICAL,
            StockItem.STEEL, StockItem.CONSTRUCTION, StockItem.TRANSPORTATION, StockItem.MEDIA_ENTERTAINMENT,
            StockItem.IT, StockItem.UTILITIES};

//    @Scheduled(cron = "0 50 16 * * MON-FRI", zone = "Asia/Seoul")
    public void saveDailyStockPrice() {
        log.info("주식 스케쥴링 시작: " + LocalDateTime.now());
        List<StockPrice> stockPrices = new ArrayList<>();

        try {
            String token = stockService.getAccessToken().getAccessToken();

            for(int i = 0; i < stockCodes.length; i++) {
                BigDecimal currentStockPrice = stockService.getCurrentStockPrice(token, stockCodes[i]);
                Stock stock = stockRepository.findByStockItem(stockItems[i])
                        .orElseThrow(() -> new NoSuchElementException("Stock 엔티티 조회에 실패했습니다."));
                StockPrice stockPrice = new StockPrice(stock, currentStockPrice);
                stockPrices.add(stockPrice);
            }

            stockService.saveCurrentStockPrices(stockPrices);
        } catch (Exception e) {
            log.error("지수 현재 가격 api 요청 에러 발생: ", e);
        }
    }
}
