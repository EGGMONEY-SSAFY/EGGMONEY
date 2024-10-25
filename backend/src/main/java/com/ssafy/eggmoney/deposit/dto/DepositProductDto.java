package com.ssafy.eggmoney.deposit.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DepositProductDto {
    private long productId;
    private String productName;
    private double depositRate;
    private int depositDate;
}
