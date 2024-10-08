package com.ssafy.eggmoney.stock.controller;

import com.ssafy.eggmoney.stock.dto.request.PendingTradeRequest;
import com.ssafy.eggmoney.stock.dto.response.StockPendingResponse;
import com.ssafy.eggmoney.stock.entity.TradeType;
import com.ssafy.eggmoney.stock.service.StockPendingService;
import com.ssafy.eggmoney.stock.service.StockUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class StockPendingController {
    private final StockPendingService stockPendingService;
    private final StockUserService stockUserService;

    @PostMapping("/stock/pending/buy")
    public ResponseEntity<Void> buyPending(@RequestBody PendingTradeRequest pendingReq,
                                              @RequestHeader("Authorization") String token) {
        Object investablePriceObj = stockUserService.findInvestablePrice(1L).get("investablePrice");
        Object balanceObj = stockUserService.findInvestablePrice(1L).get("balance");
        int pendingPrice = pendingReq.getPendingPrice() * pendingReq.getPendingAmount();

        if(investablePriceObj instanceof Integer && balanceObj instanceof Integer) {
            int investablePrice = (Integer) investablePriceObj;
            int balance = (Integer) balanceObj;

            if(pendingPrice > investablePrice || pendingPrice > balance) {
                throw new IllegalArgumentException("지정 매수 금액은 투자 가능 금액이나 잔액 보다 클 수 없습니다.");
            }

            stockPendingService.saveStockPending(pendingReq, TradeType.BUY, 1L);

            return ResponseEntity.ok().build();
        } else {
            throw new IllegalStateException("투자 가능 금액을 찾을 수 없습니다.");
        }
    }

    @PostMapping("/stock/pending/sell")
    public ResponseEntity<Void> sellPending(@RequestBody PendingTradeRequest pendingReq,
                                            @RequestHeader("Authorization") String token) {
        stockPendingService.saveStockPending(pendingReq, TradeType.SELL, 1L);
        return ResponseEntity.ok().build();
    }

    @GetMapping("stock/pending/log")
    public ResponseEntity<List<StockPendingResponse>> getStockPendingLog(@RequestHeader("Authorization") String token) {
        return new ResponseEntity<>(stockPendingService.findPendingLog(1L), HttpStatus.OK);
    }

    @PostMapping("/stock/pending/{stockPendingId}/cancel")
    public ResponseEntity<Void> cancelStockPending(@PathVariable Long stockPendingId,
                                                   @RequestHeader("Authorization") String token) {
        stockPendingService.deleteStockPending(stockPendingId, 1L);
        return ResponseEntity.ok().build();
    }
}
