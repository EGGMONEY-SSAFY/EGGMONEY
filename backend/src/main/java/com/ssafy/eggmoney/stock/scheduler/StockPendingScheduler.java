package com.ssafy.eggmoney.stock.scheduler;

import com.ssafy.eggmoney.account.entity.AccountLogType;
import com.ssafy.eggmoney.account.service.AccountService;
import com.ssafy.eggmoney.notification.dto.request.NotificationRequest;
import com.ssafy.eggmoney.notification.entity.NotificationType;
import com.ssafy.eggmoney.notification.service.NotificationService;
import com.ssafy.eggmoney.stock.entity.Stock;
import com.ssafy.eggmoney.stock.entity.StockPending;
import com.ssafy.eggmoney.stock.entity.StockUser;
import com.ssafy.eggmoney.stock.entity.TradeType;
import com.ssafy.eggmoney.stock.repository.StockPendingRepository;
import com.ssafy.eggmoney.stock.repository.StockRepository;
import com.ssafy.eggmoney.stock.repository.StockUserRepository;
import com.ssafy.eggmoney.stock.service.StockLogService;
import com.ssafy.eggmoney.stock.service.StockPendingService;
import com.ssafy.eggmoney.user.entity.User;
import com.ssafy.eggmoney.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class StockPendingScheduler {
    private final StockPendingRepository stockPendingRepository;
    private final StockPendingService stockPendingService;
    private final StockUserRepository stockUserRepository;
    private final UserRepository userRepository;
    private final StockRepository stockRepository;
    private final StockLogService stockLogService;
    private final AccountService accountService;
    private final NotificationService notificationService;

    String[] stockItems = {"???", "코스피", "코스닥", "자동차", "반도체", "헬스케어", "은행",
            "에너지화학", "철강", "건설", "운송", "미디어", "IT", "유틸리티"};

    @Scheduled(cron = "0 31 17 * * MON-FRI", zone = "Asia/Seoul")
    @Transactional
    public void executePending() {
        log.info("지정매도 시작: " + LocalDateTime.now());
        List<StockPending> stockPendingSells = stockPendingRepository.findPendingSell();

        for (StockPending stockPendingSell : stockPendingSells) {
            Optional<StockUser> stockUserOptional = stockUserRepository.findJoinStockByUserIdAndStockId(
                    stockPendingSell.getUser().getId(), stockPendingSell.getStock().getId()
            );

            if(stockUserOptional.isPresent()) {
                StockUser stockUser = stockUserOptional.get();
                stockUser.sellStock(stockPendingSell.getPendingAmount());

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

                // 알림 생성
                String Message = stockItems[stockUser.getStock().getId().intValue()] + " "
                        + stockPendingSell.getPendingAmount() + "개를 개당 "
                        + stockPendingSell.getPendingPrice() + "에 매도하였습니다.";
                NotificationRequest notificationReq = new NotificationRequest(
                        NotificationType.지정가매도체결, Message, stockPendingSell.getUser().getId()
                );
                notificationService.saveNotification(null, notificationReq);
            } else {
                log.error("[에러] stockPending" + stockPendingSell.getId() + " 실패");
            }
        }

        log.info("지정매수 시작: " + LocalDateTime.now());
        List<StockPending> stockPendingBuys = stockPendingRepository.findPendingBuy();
        for (StockPending stockPendingBuy : stockPendingBuys) {
            Optional<Stock> stockOptional = stockRepository.findById(stockPendingBuy.getStock().getId());
            Optional<User> userOptional = userRepository.findById(stockPendingBuy.getUser().getId());

            if (stockOptional.isEmpty() || userOptional.isEmpty()) {
                log.error("[에러] stockPending" + stockPendingBuy.getId() + " 실패");
                continue;
            }

            Stock stock = stockOptional.get();
            User user = userOptional.get();

            accountService.updateAccount(
                    AccountLogType.STOCK, user.getId(),
                    stockPendingBuy.getPendingPrice() * stockPendingBuy.getPendingAmount() * -1
            );

            StockUser stockUser = stockUserRepository.findByUserIdAndStockId(
                    user.getId(), stock.getId()
            ).map(stockUserExist -> {
                stockUserExist.buyStock(stockPendingBuy.getPendingPrice(), stockPendingBuy.getPendingAmount());
                return stockUserExist;
            }).orElseGet(() -> {
                StockUser newStockUser = new StockUser(user, stock, stockPendingBuy.getPendingPrice(), stockPendingBuy.getPendingAmount());
                stockUserRepository.save(newStockUser);
                return newStockUser;
            });

            stockLogService.saveStockLog(stockUser, TradeType.BUY, stockPendingBuy.getPendingPrice(), stockPendingBuy.getPendingAmount());

            stockPendingService.deleteStockPending(
                    stockPendingBuy.getId(), stockPendingBuy.getUser().getId()
            );

            // 알림 생성
            String Message = stockItems[stock.getId().intValue()] + " "
                    + stockPendingBuy.getPendingAmount() + "개를 개당 "
                    + stockPendingBuy.getPendingPrice() + "에 매수하였습니다.";
            NotificationRequest notificationReq = new NotificationRequest(
                    NotificationType.지정가매수체결, Message, stockPendingBuy.getUser().getId()
            );
            notificationService.saveNotification(null, notificationReq);
        }
    }
}
