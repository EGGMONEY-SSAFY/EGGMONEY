package com.ssafy.eggmoney.stock.service;

import com.ssafy.eggmoney.stock.dto.request.PendingTradeRequest;
import com.ssafy.eggmoney.stock.dto.response.StockPendingResponse;
import com.ssafy.eggmoney.stock.entity.Stock;
import com.ssafy.eggmoney.stock.entity.StockPending;
import com.ssafy.eggmoney.stock.entity.TradeType;
import com.ssafy.eggmoney.stock.repository.StockPendingRepository;
import com.ssafy.eggmoney.stock.repository.StockRepository;
import com.ssafy.eggmoney.user.entity.User;
import com.ssafy.eggmoney.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StockPendingServiceImpl implements StockPendingService {
    private final StockPendingRepository stockPendingRepository;
    private final StockRepository stockRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public void saveStockPending(PendingTradeRequest pendingTradeReq, TradeType tradeType, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("[증권] 해당 유저를 찾을 수 없습니다."));
        Stock stock = stockRepository.findById(pendingTradeReq.getStockId())
                .orElseThrow(() -> new NoSuchElementException("[증권] 해당 주식을 찾을 수 없습니다."));

        stockPendingRepository.save(new StockPending(
                user, stock, tradeType, pendingTradeReq.getPendingPrice(), pendingTradeReq.getPendingAmount())
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
    public int findIndividualPendingAmount(Long userId, Long stockId, TradeType tradeType) {
        List<StockPending> stockPendings = stockPendingRepository.findByUserIdAndStockIdAndTradeType(userId, stockId, tradeType);

        int pendigAmounts = 0;
        for(StockPending stockPending : stockPendings) {
            pendigAmounts += stockPending.getPendingAmount();
        }

        return pendigAmounts;
    }

    @Override
    public int findIndividualPendingPrice(Long userId, Long stockId, TradeType tradeType) {
        List<StockPending> stockPendings = stockPendingRepository.findByUserIdAndStockIdAndTradeType(userId, stockId, tradeType);

        int pendigPrices = 0;
        for(StockPending stockPending : stockPendings) {
            pendigPrices += stockPending.getPendingPrice();
        }

        return pendigPrices;
    }

    @Override
    public List<StockPendingResponse> findPendingLog(Long userId) {
        List<StockPending> stockPendings = stockPendingRepository.findByUserIdOrderByCreatedAtDesc(userId);

        if(stockPendings.isEmpty()) {
            throw new NoSuchElementException("[증권] 지정 거래 예약을 찾을 수 없습니다.");
        }

        return stockPendings.stream().map(stockPending ->
            new StockPendingResponse(
                    stockPending.getStock().getId(), stockPending.getId(), stockPending.getTradeType(),
                    stockPending.getPendingPrice(), stockPending.getPendingAmount(), stockPending.getUpdatedAt()
            )).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void deleteStockPending(Long stockPendingId,Long userId) {
        StockPending stockPending = stockPendingRepository.findById(stockPendingId)
                .orElseThrow(() -> new NoSuchElementException("[증권] 해당 지정거래를 찾을 수 없습니다."));

        if(!stockPending.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("[증권] 본인의 지정 거래만 취소하실 수 있습니다.");
        }

        stockPendingRepository.deleteById(stockPendingId);
    }
}
