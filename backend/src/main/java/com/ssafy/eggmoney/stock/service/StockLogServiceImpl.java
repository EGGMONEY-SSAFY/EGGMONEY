package com.ssafy.eggmoney.stock.service;

import com.ssafy.eggmoney.stock.dto.response.StockLogResponse;
import com.ssafy.eggmoney.stock.entity.StockLog;
import com.ssafy.eggmoney.stock.entity.StockUser;
import com.ssafy.eggmoney.stock.entity.TradeType;
import com.ssafy.eggmoney.stock.repository.StockLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StockLogServiceImpl implements StockLogService {
    private final StockLogRepository stockLogRepository;

    @Transactional
    public void saveStockLog(StockUser stockUser, TradeType tradeType, int price, int amount) {
        stockLogRepository.save(new StockLog(stockUser, tradeType, price, amount));
    }

    public List<StockLogResponse> findStockLogByUserId(Long userId) {
        List<StockLog> stockLogs = stockLogRepository.findByUserId(userId);

        if (stockLogs.isEmpty()) {
            throw new NoSuchElementException("주식 거래 내역을 찾을 수 없습니다.");
        }

        return stockLogs.stream()
                .map(stockLog -> new StockLogResponse(
                        stockLog.getStockUser().getStock().getId(), stockLog.getCreatedAt(),
                        stockLog.getTradeType(), stockLog.getTradePrice(), stockLog.getTradeAmount()
                )).collect(Collectors.toList());
    }
}
