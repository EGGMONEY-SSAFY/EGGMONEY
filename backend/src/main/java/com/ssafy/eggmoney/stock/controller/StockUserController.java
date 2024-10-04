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

    @GetMapping("/stock/user/available-balance")
    public ResponseEntity<Map<String, Object>> getInvestablePrice(@RequestHeader("Authorization") String token) {
        return new ResponseEntity<>(stockUserService.findInvestablePrice(1L), HttpStatus.OK);
    }

    @GetMapping("/stock/user/portfolio")
    public ResponseEntity<Map<String, Object>> getUserStocks(@RequestHeader("Authorization") String token) {
        return new ResponseEntity<>(stockUserService.findUserStocks(1L), HttpStatus.OK);
    }

    @PostMapping("/stock/user/buy")
    public ResponseEntity<Map<String, Object>> buyStock(
            @RequestBody StockBuyRequest stockBuyReq, @RequestHeader("Authorization") String token) {
        StockUserResponse stockBuyRes = stockUserService.buyStock(stockBuyReq, 1L);
        Map<String, Object> response = stockUserService.findInvestablePrice(stockBuyReq.getUserId());
        response.put("stockInfo", stockBuyRes);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/stock/user/sell")
    public ResponseEntity<Map<String, Object>> sellStock(@RequestBody StockSellRequest stockSellReq,
                                                         @RequestHeader("Authorization") String token) {
        StockUserResponse stockSellRes = stockUserService.sellStock(stockSellReq, 1L);
        Map<String, Object> response = stockUserService.findInvestablePrice(stockSellReq.getUserId());
        response.put("stockInfo", stockSellRes);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/stock/{stockId}/user/info")
    public ResponseEntity<StockUserResponse> getStockUser(@PathVariable Long stockId, @RequestHeader("Authorization") String token) {
        return new ResponseEntity<>(stockUserService.findStockUserInfo(stockId, 1L), HttpStatus.OK);
    }

    @GetMapping("/stock/user/log")
    public ResponseEntity<List<StockLogResponse>> getStockLogByUser(@RequestHeader("Authorization") String token) {
        return new ResponseEntity<>(stockLogService.findStockLogByUserId(1L), HttpStatus.OK);
    }
}
