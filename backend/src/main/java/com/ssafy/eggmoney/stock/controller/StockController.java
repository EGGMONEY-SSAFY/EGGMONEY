package com.ssafy.eggmoney.stock.controller;

import com.ssafy.eggmoney.stock.dto.response.StockPriceForYearResponse;
import com.ssafy.eggmoney.stock.dto.response.StockPriceResponse;
import com.ssafy.eggmoney.stock.entity.StockItem;
import com.ssafy.eggmoney.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        List<StockPriceResponse> stockPrices = stockService.findLatestStockPrices();
        response.put("stockPrices", stockPrices);

        LocalDate date = stockService.findLatestDate().toLocalDate();
        response.put("date", date);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/stock/price/year")
    public ResponseEntity<List<StockPriceForYearResponse>> getStockPricesForYear(@RequestParam StockItem stockItem) {
        return new ResponseEntity<>(stockService.findStockPricesForYear(stockItem), HttpStatus.OK);
    }
}
