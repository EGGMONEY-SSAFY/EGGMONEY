package com.ssafy.eggmoney.stock.service;

import com.ssafy.eggmoney.account.dto.responseDto.GetAnalyticsResponseDto;
import com.ssafy.eggmoney.account.entity.AccountLogType;
import com.ssafy.eggmoney.account.service.AccountService;
import com.ssafy.eggmoney.stock.dto.request.StockBuyRequest;
import com.ssafy.eggmoney.stock.dto.request.StockSellRequest;
import com.ssafy.eggmoney.stock.dto.response.StockBuyResponse;
import com.ssafy.eggmoney.stock.dto.response.StockSellResponse;
import com.ssafy.eggmoney.stock.entity.Stock;
import com.ssafy.eggmoney.stock.entity.StockUser;
import com.ssafy.eggmoney.stock.entity.TradeType;
import com.ssafy.eggmoney.stock.repository.StockUserRepository;
import com.ssafy.eggmoney.user.entity.User;
import com.ssafy.eggmoney.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StockUserServiceImpl implements StockUserService {
    private final StockUserRepository stockUserRepository;
    private final AccountService accountService;
    private final StockService stockService;
    private final UserService userService;
    private final StockLogService stockLogService;

    @Transactional
    @Override
    public Map<String, Object> findInvestableRatio(Long userId) {
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

        response.put("investablePrice", investablePrice);
        response.put("balance", analytics.getMainAccountBalance());
        return response;
    }

    @Transactional
    @Override
    public StockBuyResponse buyStock(StockBuyRequest stockBuyReq) {
        Stock stock = stockService.findByStockItemAndDate(stockBuyReq.getStockItem());
        User user = userService.findUserEntity(stockBuyReq.getUserId());

        accountService.updateAccount(AccountLogType.STOCK, stockBuyReq.getUserId(),
                stockBuyReq.getAmount() * stock.getStockPrice() * -1);

        StockUser stockUser = stockUserRepository.findByStockIdAndUserId(stock.getId(), user.getId())
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

        return new StockBuyResponse(stockUser.getBuyAverage(), stockUser.getAmount(), stock.getStockPrice());
    }

    @Transactional
    @Override
    public StockSellResponse sellStock(StockSellRequest stockSellReq) {
        Stock stock = stockService.findByStockItemAndDate(stockSellReq.getStockItem());
        User user = userService.findUserEntity(stockSellReq.getUserId());

        accountService.updateAccount(AccountLogType.STOCK, stockSellReq.getUserId(),
                stockSellReq.getAmount() * stock.getStockPrice());

        StockUser stockUser = stockUserRepository.findByStockIdAndUserId(stock.getId(), user.getId())
                .map(existingStockUser -> {
                    existingStockUser.sellStock(stockSellReq.getAmount());
                    return existingStockUser;
                })
                .orElseThrow(() -> new IllegalArgumentException("팔수 있는 주식이 존재하지 않습니다."));

        stockLogService.saveStockLog(stockUser, TradeType.SELL, stock.getStockPrice(), stockSellReq.getAmount());

        if(stockUser.getAmount() == 0) {
            return new StockSellResponse();
        } else {
            return new StockSellResponse(stockUser.getBuyAverage(), stockUser.getAmount(), stock.getStockPrice());
        }
    }
}
