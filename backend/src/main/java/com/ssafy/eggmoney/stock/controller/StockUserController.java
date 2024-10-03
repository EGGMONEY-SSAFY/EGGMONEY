package com.ssafy.eggmoney.stock.controller;

import com.ssafy.eggmoney.stock.dto.request.StockBuyRequest;
import com.ssafy.eggmoney.stock.dto.request.StockSellRequest;
import com.ssafy.eggmoney.stock.dto.request.StockUserRequest;
import com.ssafy.eggmoney.stock.dto.response.StockLogResponse;
import com.ssafy.eggmoney.stock.dto.response.StockUserResponse;
import com.ssafy.eggmoney.stock.service.StockLogService;
import com.ssafy.eggmoney.stock.service.StockUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class StockUserController {
    private final StockUserService stockUserService;
    private final StockLogService stockLogService;

    @GetMapping("/stock/user/{userId}/available-balance")
    public ResponseEntity<Map<String, Object>> getInvestableRatio(@PathVariable Long userId) {
        return new ResponseEntity<>(stockUserService.findInvestablePrice(userId), HttpStatus.OK);
    }

    @GetMapping("/stock/user/{userId}/portfolio")
    public ResponseEntity<Map<String, Object>> getUserStocks(@PathVariable Long userId) {
        return new ResponseEntity<>(stockUserService.findUserStocks(userId), HttpStatus.OK);
    }

    @PostMapping("/stock/user/buy")
    public ResponseEntity<Map<String, Object>> buyStock(@RequestBody StockBuyRequest stockBuyReq) {
        StockUserResponse stockBuyRes = stockUserService.buyStock(stockBuyReq);
        Map<String, Object> response = stockUserService.findInvestablePrice(stockBuyReq.getUserId());
        response.put("stockInfo", stockBuyRes);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/stock/user/sell")
    public ResponseEntity<Map<String, Object>> sellStock(@RequestBody StockSellRequest stockSellReq) {
        StockUserResponse stockSellRes = stockUserService.sellStock(stockSellReq);
        Map<String, Object> response = stockUserService.findInvestablePrice(stockSellReq.getUserId());
        response.put("stockInfo", stockSellRes);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/stock/user/info")
    public ResponseEntity<StockUserResponse> getStockUser(@RequestBody StockUserRequest stockUserReq) {
        return new ResponseEntity<>(stockUserService.findStockUserInfo(stockUserReq), HttpStatus.OK);
    }

    @GetMapping("/stock/user/{userId}/log")
    public ResponseEntity<List<StockLogResponse>> getStockLogByUser(@PathVariable Long userId) {
        return new ResponseEntity<>(stockLogService.findStockLogByUserId(userId), HttpStatus.OK);
    }
}
