package com.ssafy.eggmoney.stock.service;

import com.ssafy.eggmoney.stock.entity.StockLog;
import com.ssafy.eggmoney.stock.entity.StockUser;
import com.ssafy.eggmoney.stock.entity.TradeType;
import com.ssafy.eggmoney.stock.repository.StockLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StockLogServiceImpl implements StockLogService {
    private final StockLogRepository stockLogRepository;

    @Transactional
    public void saveStockLog(StockUser stockUser, TradeType tradeType, int price, int amount) {
        stockLogRepository.save(new StockLog(stockUser, tradeType, price, amount));
    }
}
