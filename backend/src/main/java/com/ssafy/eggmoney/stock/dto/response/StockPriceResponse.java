package com.ssafy.eggmoney.stock.dto.response;

import com.ssafy.eggmoney.stock.entity.StockItem;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Getter
public class StockPriceResponse {
    private Long stockId;
    private StockItem stockItem;
    private LocalDateTime updatedDate;
    private int price;
    private int gap;
    private BigDecimal ratio;

    public StockPriceResponse(Long stockId, StockItem stockItem, LocalDateTime updatedDate,
                              int price, int previousPrice) {
        this.stockId = stockId;
        this.stockItem = stockItem;
        this.updatedDate = updatedDate;
        this.price = price;
        this.gap = price - previousPrice;

        BigDecimal priceBD = BigDecimal.valueOf(price);
        BigDecimal previousPriceBD = BigDecimal.valueOf(previousPrice);
        BigDecimal result = priceBD.subtract(previousPriceBD)
                .divide(previousPriceBD, 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
        ratio = result.setScale(2, RoundingMode.HALF_UP);
    }
}
