package com.ssafy.eggmoney.stock.controller;

import com.ssafy.eggmoney.stock.dto.request.StockBuyRequest;
import com.ssafy.eggmoney.stock.dto.request.StockSellRequest;
import com.ssafy.eggmoney.stock.dto.response.StockBuyResponse;
import com.ssafy.eggmoney.stock.dto.response.StockSellResponse;
import com.ssafy.eggmoney.stock.service.StockUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class StockUserController {
    private final StockUserService stockUserService;

    @GetMapping("/stock/user/{userId}/available-balance")
    public ResponseEntity<Map<String, Object>> getInvestableRatio(@PathVariable Long userId) {
        return new ResponseEntity<>(stockUserService.findInvestableRatio(userId), HttpStatus.OK);
    }

    @PostMapping("/stock/user/buy")
    public ResponseEntity<Map<String, Object>> buyStock(@RequestBody StockBuyRequest stockBuyReq) {
        StockBuyResponse stockBuyRes = stockUserService.buyStock(stockBuyReq);
        Map<String, Object> response = stockUserService.findInvestableRatio(stockBuyReq.getUserId());
        response.put("stockInfo", stockBuyRes);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/stock/user/sell")
    public ResponseEntity<Map<String, Object>> sellStock(@RequestBody StockSellRequest stockSellReq) {
        StockSellResponse stockSellRes = stockUserService.sellStock(stockSellReq);
        Map<String, Object> response = stockUserService.findInvestableRatio(stockSellReq.getUserId());
        response.put("stockInfo", stockSellRes);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
