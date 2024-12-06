package com.ssafy.eggmoney.deposit.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DepositProductListResponseDto {
    private Long productId;
    private String productName;
    private int depositDate;
    private Double depositRate;
}
