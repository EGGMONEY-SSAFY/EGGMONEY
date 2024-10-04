package com.ssafy.eggmoney.stock.service;

import com.ssafy.eggmoney.stock.dto.request.PendingRequest;
import com.ssafy.eggmoney.stock.entity.Stock;
import com.ssafy.eggmoney.stock.entity.StockPending;
import com.ssafy.eggmoney.stock.entity.TradeType;
import com.ssafy.eggmoney.stock.repository.StockPendingRepository;
import com.ssafy.eggmoney.stock.repository.StockRepository;
import com.ssafy.eggmoney.user.entity.User;
import com.ssafy.eggmoney.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StockPendingServiceImpl implements StockPendingService {
    private final StockPendingRepository stockPendingRepository;
    private final StockRepository stockRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public void saveStockPending(PendingRequest pendingReq, TradeType tradeType, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("해당 유저를 찾을 수 없습니다."));
        Stock stock = stockRepository.findById(pendingReq.getStockId())
                .orElseThrow(() -> new NoSuchElementException("해당 주식을 찾을 수 없습니다."));

        stockPendingRepository.save(new StockPending(
                user, stock, tradeType, pendingReq.getPendingPrice(), pendingReq.getPendingAmount())
        );
    }

    @Override
    public int findPendingBuyTotalPrice(Long userId) {
        List<StockPending> stockPendings = stockPendingRepository.findByUserIdAndTradeType(userId, TradeType.BUY);

        int totalPendigPrice = 0;
        for(StockPending stockPending : stockPendings) {
            totalPendigPrice += stockPending.getPendingPrice() * stockPending.getPendingAmount();
        }

        return totalPendigPrice;
    }

    @Override
    public int findPendingSellTotalAmount(Long userId) {
        List<StockPending> stockPendings = stockPendingRepository.findByUserIdAndTradeType(userId, TradeType.SELL);

        int totalPendigAmount = 0;
        for(StockPending stockPending : stockPendings) {
            totalPendigAmount += stockPending.getPendingAmount();
        }

        return totalPendigAmount;
    }
}
