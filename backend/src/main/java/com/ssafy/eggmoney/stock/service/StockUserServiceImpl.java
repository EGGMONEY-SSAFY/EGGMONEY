package com.ssafy.eggmoney.stock.service;

import com.ssafy.eggmoney.account.dto.responseDto.GetAnalyticsResponseDto;
import com.ssafy.eggmoney.account.entity.AccountLogType;
import com.ssafy.eggmoney.account.service.AccountService;
import com.ssafy.eggmoney.stock.dto.request.StockBuyRequest;
import com.ssafy.eggmoney.stock.dto.request.StockSellRequest;
import com.ssafy.eggmoney.stock.dto.request.StockUserRequest;
import com.ssafy.eggmoney.stock.dto.response.StockUserResponse;
import com.ssafy.eggmoney.stock.entity.Stock;
import com.ssafy.eggmoney.stock.entity.StockItem;
import com.ssafy.eggmoney.stock.entity.StockUser;
import com.ssafy.eggmoney.stock.entity.TradeType;
import com.ssafy.eggmoney.stock.repository.StockRepository;
import com.ssafy.eggmoney.stock.repository.StockUserRepository;
import com.ssafy.eggmoney.user.entity.User;
import com.ssafy.eggmoney.user.service.UserService;
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
    private final UserService userService;
    private final StockLogService stockLogService;
    private final StockRepository stockRepository;
    private final StockService stockService;

    @Transactional
    @Override
    public Map<String, Object> findInvestablePrice(Long userId) {
        Map<String, Object> response = new HashMap<>();

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

        BigDecimal investableRatio = BigDecimal.valueOf(userService.findInvestableRatio(userId));
        int investablePrice = investableRatio
                .divide(BigDecimal.valueOf(100))
                .multiply(BigDecimal.valueOf(assets))
                .setScale(0, RoundingMode.HALF_UP)
                .intValue();

        investablePrice -= accountService.findUserTotalStockPrice(userId);

        response.put("investablePrice", investablePrice);
        response.put("balance", analytics.getMainAccountBalance());
        return response;
    }

    @Override
    public Map<String, Object> findUserStocks(Long userId) {
        Map<String, Object> response = new HashMap<>();
        int totalPrice = 0;
        int[] prices = new int[13];

        List<Stock> stocks = stockRepository.findTop13ByOrderByUpdatedAtDesc();
        List<StockUser> stockUsers = stockUserRepository.findJoinByUserId(userId);
        StockItem[] stockItems = stockService.getStockItems();

        for(int i = 0; i < stockItems.length; i++) {
            for(StockUser stockUser : stockUsers) {
                if(stockItems[i] == stockUser.getStock().getStockItem()) {
                    int price = stocks.get(stockItems.length - i - 1).getStockPrice() * stockUser.getAmount();
                    prices[i] = price;
                    totalPrice += price;
                }
            }
        }

        response.put("stockItems", stockItems);
        response.put("prices", prices);
        response.put("totalPrice", totalPrice);

        return response;
    }

    @Transactional
    @Override
    public StockUserResponse buyStock(StockBuyRequest stockBuyReq) {
        List<Stock> stocks = stockRepository.findTop2ByStockItemOrderByUpdatedAtDesc(stockBuyReq.getStockItem());
        if(stocks.isEmpty()) {
            throw new NoSuchElementException("최신 주식 가격이 조회 되지 않습니다.");
        }
        Stock stock = stocks.get(0);

        User user = userService.findUserEntity(stockBuyReq.getUserId());

        accountService.updateAccount(AccountLogType.STOCK, stockBuyReq.getUserId(),
                stockBuyReq.getAmount() * stock.getStockPrice() * -1);

        StockUser stockUser = stockUserRepository.findByUserIdAndStockItem(
                stockBuyReq.getUserId(), stockBuyReq.getStockItem())
                .map(existingStockUser -> {
                    existingStockUser.buyStock(stock.getStockPrice(), stockBuyReq.getAmount());
                    return existingStockUser;
                })
                .orElseGet(() -> {
                    StockUser newStockUser = new StockUser(user, stock, stock.getStockPrice(), stockBuyReq.getAmount());
                    stockUserRepository.save(newStockUser);
                    return newStockUser;
                });

        stockLogService.saveStockLog(stockUser, TradeType.BUY, stock.getStockPrice(), stockBuyReq.getAmount());

        return new StockUserResponse(stockUser.getBuyAverage(), stockUser.getAmount(), stock.getStockPrice());
    }

    @Transactional
    @Override
    public StockUserResponse sellStock(StockSellRequest stockSellReq) {
        List<Stock> stocks = stockRepository.findTop2ByStockItemOrderByUpdatedAtDesc(stockSellReq.getStockItem());
        if(stocks.isEmpty()) {
            throw new NoSuchElementException("최신 주식 가격이 조회 되지 않습니다.");
        }
        Stock stock = stocks.get(0);

        StockUser stockUser = stockUserRepository.findByUserIdAndStockItem(
                stockSellReq.getUserId(), stockSellReq.getStockItem())
                .map(existingStockUser -> {
                    existingStockUser.sellStock(stockSellReq.getAmount());
                    return existingStockUser;
                })
                .orElseThrow(() -> new IllegalArgumentException("팔수 있는 주식이 존재하지 않습니다."));

        stockLogService.saveStockLog(stockUser, TradeType.SELL, stock.getStockPrice(), stockSellReq.getAmount());

        accountService.updateAccount(AccountLogType.STOCK, stockSellReq.getUserId(),
                stockSellReq.getAmount() * stock.getStockPrice());

        if (stockUser.getAmount() == 0) {
            return new StockUserResponse();
        } else {
            return new StockUserResponse(stockUser.getBuyAverage(), stockUser.getAmount(), stock.getStockPrice());
        }
    }

    @Override
    public StockUserResponse findStockUserInfo(StockUserRequest stockUserReq) {
        List<Stock> stocks = stockRepository.findTop2ByStockItemOrderByUpdatedAtDesc(stockUserReq.getStockItem());
        if(stocks.isEmpty()) {
            throw new NoSuchElementException("최신 주식 가격이 조회 되지 않습니다.");
        }
        Stock stock = stocks.get(0);

        return stockUserRepository.findByUserIdAndStockItem(
                stockUserReq.getUserId(), stockUserReq.getStockItem())
                .map(stockUser -> new StockUserResponse(
                        stockUser.getBuyAverage(), stockUser.getAmount(), stock.getStockPrice()
                )).orElseGet(StockUserResponse::new);
    }
}
