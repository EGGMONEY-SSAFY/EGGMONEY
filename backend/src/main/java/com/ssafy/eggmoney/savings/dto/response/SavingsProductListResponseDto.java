package com.ssafy.eggmoney.savings.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SavingsProductListResponseDto {
    private Long id;
    private String productName;
    private int savingsDate;
    private Double savingsRate;
    private int maxPrice;
}
