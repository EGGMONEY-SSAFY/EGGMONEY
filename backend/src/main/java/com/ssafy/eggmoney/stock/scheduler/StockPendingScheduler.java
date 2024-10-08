package com.ssafy.eggmoney.stock.scheduler;

import com.ssafy.eggmoney.account.entity.AccountLogType;
import com.ssafy.eggmoney.account.service.AccountService;
import com.ssafy.eggmoney.stock.entity.StockPending;
import com.ssafy.eggmoney.stock.entity.StockUser;
import com.ssafy.eggmoney.stock.entity.TradeType;
import com.ssafy.eggmoney.stock.repository.StockPendingRepository;
import com.ssafy.eggmoney.stock.repository.StockUserRepository;
import com.ssafy.eggmoney.stock.service.StockLogService;
import com.ssafy.eggmoney.stock.service.StockPendingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class StockPendingScheduler {
    private final StockPendingRepository stockPendingRepository;
    private final StockPendingService stockPendingService;
    private final StockUserRepository stockUserRepository;
    private final StockLogService stockLogService;
    private final AccountService accountService;

    @Scheduled(cron = "0 24 14 * * MON-FRI", zone = "Asia/Seoul")
    @Transactional
    public void executePending() {
        log.info("지정매도 시작: " + LocalDateTime.now());
        List<StockPending> stockPendingSells = stockPendingRepository.findPendingSell();

        for (StockPending stockPendingSell : stockPendingSells) {
            StockUser stockUser = stockUserRepository.findJoinStockByUserIdAndStockId(
                    stockPendingSell.getUser().getId(), stockPendingSell.getStock().getId()
            ).map(stockUserExist -> {
                stockUserExist.sellStock(stockPendingSell.getPendingAmount());
                return stockUserExist;
            }).orElseThrow(() -> new IllegalArgumentException("팔 수 있는 주식이 존재하지 않습니다."));

            stockLogService.saveStockLog(
                    stockUser, TradeType.SELL, stockPendingSell.getPendingPrice(),
                    stockPendingSell.getPendingAmount()
            );

            accountService.updateAccount(
                    AccountLogType.STOCK, stockPendingSell.getUser().getId(),
                    stockPendingSell.getPendingPrice() * stockPendingSell.getPendingAmount()
            );

            stockPendingService.deleteStockPending(
                    stockPendingSell.getId(), stockPendingSell.getUser().getId()
            );
        }

        log.info("지정매수 시작: " + LocalDateTime.now());
        
    }
}
