package com.ssafy.eggmoney.stock.scheduler;

import com.ssafy.eggmoney.stock.entity.StockItem;
import com.ssafy.eggmoney.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.codec.DecodingException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

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

    @Scheduled(cron = "0 0 11 * * MON-FRI")
    public void saveDailyStockPrice() {
        String token = stockService.getAccessToken().getAccessToken();

        try {
            for(int i = 0; i < stockCodes.length; i++) {
                BigDecimal currentStockPrice = stockService.getCurrentStockPrice(token, stockCodes[i]);
                stockService.saveCurrentStockPrice(stockItems[i], currentStockPrice);
            }
        } catch (DecodingException d) {
            log.info("공휴일이라서 응답 값이 없습니다.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
