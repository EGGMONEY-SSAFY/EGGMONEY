package com.ssafy.eggmoney.stock.dto.request;

import lombok.Getter;

@Getter
public class PendingRequest {
    private Long userId;
    private Long stockId;
    private int pendingPrice;
    private int pendingAmount;
}
