package com.ssafy.eggmoney.stock.service;

import com.ssafy.eggmoney.account.dto.response.GetAnalyticsResponseDto;
import com.ssafy.eggmoney.account.entity.AccountLogType;
import com.ssafy.eggmoney.account.service.AccountService;
import com.ssafy.eggmoney.stock.dto.request.StockBuyRequest;
import com.ssafy.eggmoney.stock.dto.request.StockSellRequest;
import com.ssafy.eggmoney.stock.dto.response.StockUserResponse;
import com.ssafy.eggmoney.stock.entity.*;
import com.ssafy.eggmoney.stock.repository.StockRepository;
import com.ssafy.eggmoney.stock.repository.StockUserRepository;
import com.ssafy.eggmoney.user.entity.User;
import com.ssafy.eggmoney.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StockUserServiceImpl implements StockUserService {
    private final StockUserRepository stockUserRepository;
    private final UserRepository userRepository;
    private final StockRepository stockRepository;
    private final AccountService accountService;
    private final StockLogService stockLogService;
    private final StockPendingService stockPendingService;

    @Transactional
    @Override
    public Map<String, Object> findInvestablePrice(Long userId) {
        Map<String, Object> response = new HashMap<>();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("[증권] 해당 유저를 찾을 수 없습니다.")
                );

        GetAnalyticsResponseDto analytics = accountService.getAnalytics(userId);
        int assets = 0;
        int totalStockPrice = 0;
        if(analytics.getMainAccountBalance() != null) {
            assets += analytics.getMainAccountBalance();
        }
        if(analytics.getDeposit() != null) {
            assets += analytics.getDeposit();
        }
        if(analytics.getSavings() != null) {
            assets += analytics.getSavings();
        }
        if(analytics.getLoan() != null) {
            assets += analytics.getLoan();
        }
        if(analytics.getStock() != null) {
            assets += analytics.getStock();
            totalStockPrice = analytics.getStock();
        }

        int investablePrice = BigDecimal.valueOf(user.getStockRatio())
                .divide(BigDecimal.valueOf(100))
                .multiply(BigDecimal.valueOf(assets))
                .setScale(0, RoundingMode.HALF_UP)
                .intValue();

        int totalPendingPrice = stockPendingService.findPendingBuyTotalPrice(user.getId());

        investablePrice -= totalStockPrice + totalPendingPrice;

        response.put("investablePrice", investablePrice);
        response.put("balance", analytics.getMainAccountBalance());
        response.put("totalStockPrice", totalStockPrice);
        return response;
    }

    @Override
    public Map<String, Object> findUserStocks(Long userId) {
        Map<String, Object> response = new HashMap<>();
        List<Long> stockIds = new ArrayList<>();
        List<Integer> prices = new ArrayList<>();

        List<StockUser> stockUsers = stockUserRepository.findJoinStockByUserIdOrderByStockId(userId);

        for(StockUser stockUser : stockUsers) {
            if(stockUser.getAmount() != 0) {
                int price = stockUser.getStock().getCurrentPrice() * stockUser.getAmount();
                stockIds.add(stockUser.getStock().getId());
                prices.add(price);
            }
        }

        response.put("labels", stockIds);
        response.put("prices", prices);

        return response;
    }

    @Transactional
    @Override
    public void buyStock(StockBuyRequest stockBuyReq, Long userId) {
        Stock stock = stockRepository.findById(stockBuyReq.getStockId())
                .orElseThrow(() -> new NoSuchElementException("해당 주식을 찾을 수 없습니다."));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("해당 유저를 찾을 수 없습니다."));

        accountService.updateAccount(
                AccountLogType.STOCK, userId,
                stockBuyReq.getAmount() * stock.getCurrentPrice() * -1
        );

        StockUser stockUser = stockUserRepository.findByUserIdAndStockId(
                userId, stockBuyReq.getStockId()
                ).map(stockUserExist -> {
                    stockUserExist.buyStock(stock.getCurrentPrice(), stockBuyReq.getAmount());
                    return stockUserExist;
                }).orElseGet(() -> {
                    StockUser newStockUser = new StockUser(user, stock, stock.getCurrentPrice(), stockBuyReq.getAmount());
                    stockUserRepository.save(newStockUser);
                    return newStockUser;
                });

        stockLogService.saveStockLog(stockUser, TradeType.BUY, stock.getCurrentPrice(), stockBuyReq.getAmount());
    }

    @Transactional
    @Override
    public void sellStock(StockSellRequest stockSellReq, Long userId) {
        int pendingSellAmount = stockPendingService.findIndividualPendingAmount(userId, stockSellReq.getStockId(), TradeType.SELL);

        StockUser stockUser = stockUserRepository.findJoinStockByUserIdAndStockId(
                userId, stockSellReq.getStockId()
                ).map(stockUserExist -> {
                    if(stockUserExist.getAmount() - pendingSellAmount - stockSellReq.getAmount() < 0) {
                        throw new IllegalArgumentException("판매하는 주식이 보유 주식에서 지정 매수를 뺀 수량을 초과하실 수 없습니다.");
                    }
                    stockUserExist.sellStock(stockSellReq.getAmount());
                    return stockUserExist;
                }).orElseThrow(() -> new IllegalArgumentException("팔 수 있는 주식이 존재하지 않습니다."));

        stockLogService.saveStockLog(
                stockUser, TradeType.SELL, stockUser.getStock().getCurrentPrice(), stockSellReq.getAmount()
        );

        accountService.updateAccount(
                AccountLogType.STOCK, userId,
                stockSellReq.getAmount() * stockUser.getStock().getCurrentPrice()
        );
    }

    @Override
    public StockUserResponse findStockUserInfo(Long stockId, Long userId) {
        int pendingBuyAmount = stockPendingService.findIndividualPendingAmount(userId, stockId, TradeType.BUY);
        int pendingBuyPrice = stockPendingService.findIndividualPendingPrice(userId, stockId, TradeType.BUY);
        int pendingSellAmount = stockPendingService.findIndividualPendingAmount(userId, stockId, TradeType.SELL);
        int pendingSellPrice = stockPendingService.findIndividualPendingPrice(userId, stockId, TradeType.SELL);
        return stockUserRepository.findJoinStockByUserIdAndStockId(
                userId, stockId
                ).map(stockUser -> new StockUserResponse(
                        stockUser.getBuyAverage(), stockUser.getAmount(), stockUser.getStock().getCurrentPrice(),
                        pendingBuyAmount,pendingBuyPrice, pendingSellAmount, pendingSellPrice
                )).orElseGet(StockUserResponse::new);
    }
}
