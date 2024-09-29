package com.ssafy.eggmoney.stock.controller;

import com.ssafy.eggmoney.stock.dto.request.StockBuyRequest;
import com.ssafy.eggmoney.stock.dto.response.StockUserResponse;
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
        StockUserResponse stockUserRes = stockUserService.buyStock(stockBuyReq);
        Map<String, Object> response = stockUserService.findInvestableRatio(stockBuyReq.getUserId());
        response.put("stockInfo", stockUserRes);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
