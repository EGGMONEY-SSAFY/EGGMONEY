package com.ssafy.eggmoney.stock.controller;

import com.ssafy.eggmoney.account.dto.responseDto.GetAnalyticsResponseDto;
import com.ssafy.eggmoney.account.service.AccountService;
import com.ssafy.eggmoney.stock.dto.response.StockPriceResponse;
import com.ssafy.eggmoney.stock.service.StockService;
import com.ssafy.eggmoney.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class StockController {
    private final StockService stockService;
    private final AccountService accountService;
    private final UserService userService;

    @GetMapping("/stock/price")
    public ResponseEntity<Map<String, Object>> getLatestStockPrice() {
        Map<String, Object> response = new HashMap<>();

        List<StockPriceResponse> stockPrices = stockService.findLatestStockPrice();
        response.put("stockPrices", stockPrices);

        LocalDate date = stockService.findLatestDate();
        response.put("date", date);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/stock/user/{userId}/ratio")
    public ResponseEntity<Integer> getInvestableRatio(@PathVariable Long userId) {
        GetAnalyticsResponseDto analytics = accountService.getAnalytics(userId);
        int assets = analytics.getMainAccountBalance()
                + analytics.getDeposit()
                + analytics.getSavings()
                + analytics.getLoan()
                + analytics.getStock();

        BigDecimal investableRatio = BigDecimal.valueOf(userService.findInvestableRatio(userId));

        BigDecimal investablePrice = investableRatio
                .divide(BigDecimal.valueOf(100))
                .multiply(BigDecimal.valueOf(assets));

        return new ResponseEntity<>(investablePrice.setScale(0, RoundingMode.HALF_UP).intValue(), HttpStatus.OK);
    }
}
