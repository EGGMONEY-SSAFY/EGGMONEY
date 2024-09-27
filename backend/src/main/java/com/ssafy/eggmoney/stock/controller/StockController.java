package com.ssafy.eggmoney.stock.controller;

import com.ssafy.eggmoney.stock.dto.response.StockPriceResponse;
import com.ssafy.eggmoney.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class StockController {
    private final StockService stockService;

    @GetMapping("/stock/price")
    public ResponseEntity<Map<String, Object>> getLatestStockPrice() {
        Map<String, Object> response = new HashMap<>();

        List<StockPriceResponse> stockPrices = stockService.findLatestStockPrice();
        response.put("stockPrices", stockPrices);

        LocalDate date = stockService.findLatestDate();
        response.put("date", date);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
