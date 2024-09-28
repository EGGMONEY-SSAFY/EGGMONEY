package com.ssafy.eggmoney.stock.dto.response;

import com.ssafy.eggmoney.stock.entity.StockItem;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
public class StockPriceResponse {
    private StockItem stockItem;
    private int price;
    private int gap;
    private BigDecimal ratio;

    public StockPriceResponse(StockItem stockItem, int price, int previousPrice) {
        this.stockItem = stockItem;
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
