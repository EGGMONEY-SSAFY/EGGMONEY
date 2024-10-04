package com.ssafy.eggmoney.stock.service;

import com.ssafy.eggmoney.account.dto.responseDto.GetAnalyticsResponseDto;
import com.ssafy.eggmoney.account.entity.AccountLogType;
import com.ssafy.eggmoney.account.service.AccountService;
import com.ssafy.eggmoney.stock.dto.request.StockBuyRequest;
import com.ssafy.eggmoney.stock.dto.request.StockSellRequest;
import com.ssafy.eggmoney.stock.dto.request.StockUserRequest;
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
    private final AccountService accountService;
    private final UserRepository userRepository;
    private final StockLogService stockLogService;
    private final StockRepository stockRepository;
    private final StockPendingService stockPendingService;

    @Transactional
    @Override
    public Map<String, Object> findInvestablePrice(Long userId) {
        Map<String, Object> response = new HashMap<>();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("해당 유저를 찾을 수 없습니다.")
                );

        GetAnalyticsResponseDto analytics = accountService.getAnalytics(userId);
        int assets = 0;
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
        }

        int investablePrice = BigDecimal.valueOf(user.getStockRatio())
                .divide(BigDecimal.valueOf(100))
                .multiply(BigDecimal.valueOf(assets))
                .setScale(0, RoundingMode.HALF_UP)
                .intValue();

        int totalPendingPrice = stockPendingService.findPendingBuyTotalPrice(user.getId());

        investablePrice -= accountService.findUserTotalStockPrice(userId) + totalPendingPrice;

        response.put("investablePrice", investablePrice);
        response.put("balance", analytics.getMainAccountBalance());
        return response;
    }

    @Override
    public Map<String, Object> findUserStocks(Long userId) {
        Map<String, Object> response = new HashMap<>();
        int totalPrice = 0;
        int[] prices = new int[14];

        List<StockUser> stockUsers = stockUserRepository.findJoinStockByUserIdOrderByStockId(userId);
        long stockCount = stockRepository.count();

        for(int i = 1; i <= stockCount; i++) {
            for(StockUser stockUser : stockUsers) {
                if(i == stockUser.getStock().getId()) {
                    int price = stockUser.getStock().getCurrentPrice() * stockUser.getAmount();
                    prices[i] = price;
                    totalPrice += price;
                }
            }
        }

        response.put("prices", prices);
        response.put("totalPrice", totalPrice);

        return response;
    }

    @Transactional
    @Override
    public StockUserResponse buyStock(StockBuyRequest stockBuyReq) {
        Stock stock = stockRepository.findById(stockBuyReq.getStockId())
                .orElseThrow(() -> new NoSuchElementException("해당 주식을 찾을 수 없습니다."));
        User user = userRepository.findById(stockBuyReq.getUserId())
                .orElseThrow(() -> new NoSuchElementException("해당 유저를 찾을 수 없습니다."));

        accountService.updateAccount(
                AccountLogType.STOCK, stockBuyReq.getUserId(),
                stockBuyReq.getAmount() * stock.getCurrentPrice() * -1
        );

        StockUser stockUser = stockUserRepository.findByUserIdAndStockId(
                stockBuyReq.getUserId(), stockBuyReq.getStockId()
                ).map(stockUserExist -> {
                    stockUserExist.buyStock(stock.getCurrentPrice(), stockBuyReq.getAmount());
                    return stockUserExist;
                }).orElseGet(() -> {
                    StockUser newStockUser = new StockUser(user, stock, stock.getCurrentPrice(), stockBuyReq.getAmount());
                    stockUserRepository.save(newStockUser);
                    return newStockUser;
                });

        stockLogService.saveStockLog(stockUser, TradeType.BUY, stock.getCurrentPrice(), stockBuyReq.getAmount());

        return new StockUserResponse(stockUser.getBuyAverage(), stockUser.getAmount(), stock.getCurrentPrice());
    }

    @Transactional
    @Override
    public StockUserResponse sellStock(StockSellRequest stockSellReq) {
        int pendingSellAmount = stockPendingService.findPendingSellTotalAmount(stockSellReq.getUserId());

        StockUser stockUser = stockUserRepository.findJoinStockByUserIdAndStockId(
                stockSellReq.getUserId(), stockSellReq.getStockId()
                ).map(stockUserExist -> {
                    stockUserExist.sellStock(stockSellReq.getAmount());
                    return stockUserExist;
                }).orElseThrow(() -> new IllegalArgumentException("팔 수 있는 주식이 존재하지 않습니다."));

        if(stockUser.getAmount() - pendingSellAmount - stockSellReq.getAmount() < 0) {
            throw new IllegalArgumentException("판매하는 주식이 보유 주식에서 지정 매수를 뺀 수량을 초과하실 수 없습니다.");
        }

        stockLogService.saveStockLog(
                stockUser, TradeType.SELL, stockUser.getStock().getCurrentPrice(), stockSellReq.getAmount()
        );

        accountService.updateAccount(
                AccountLogType.STOCK, stockSellReq.getUserId(),
                stockSellReq.getAmount() * stockUser.getStock().getCurrentPrice()
        );

        if (stockUser.getAmount() == 0) {
            return new StockUserResponse();
        } else {
            return new StockUserResponse(
                    stockUser.getBuyAverage(), stockUser.getAmount(), stockUser.getStock().getCurrentPrice()
            );
        }
    }

    @Override
    public StockUserResponse findStockUserInfo(StockUserRequest stockUserReq) {
        return stockUserRepository.findJoinStockByUserIdAndStockId(
                stockUserReq.getUserId(), stockUserReq.getStockId()
                ).map(stockUser -> new StockUserResponse(
                        stockUser.getBuyAverage(), stockUser.getAmount(), stockUser.getStock().getCurrentPrice()
                )).orElseGet(StockUserResponse::new);
    }
}
