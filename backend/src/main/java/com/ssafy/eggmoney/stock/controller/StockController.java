package com.ssafy.eggmoney.stock.controller;

import com.ssafy.eggmoney.stock.dto.response.StockPriceForYearResponse;
import com.ssafy.eggmoney.stock.dto.response.StockPriceResponse;
import com.ssafy.eggmoney.stock.entity.StockItem;
import com.ssafy.eggmoney.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class StockController {
    private final StockService stockService;

    @GetMapping("/stock/price")
    public ResponseEntity<List<StockPriceResponse>> getLatestStockPrice() {
        return new ResponseEntity<>(stockService.findLatestStockPrices(), HttpStatus.OK);
    }

    @GetMapping("/stock/price/year")
    public ResponseEntity<List<StockPriceForYearResponse>> getStockPricesForYear(@RequestParam StockItem stockItem) {
        return new ResponseEntity<>(stockService.findStockPricesForYear(stockItem), HttpStatus.OK);
    }
}
