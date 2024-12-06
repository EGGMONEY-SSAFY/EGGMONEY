package com.ssafy.eggmoney.savings.dto.response;


import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class SavingsLogResponseDto {
    private LocalDateTime createdAt;
    private int paymentMoney;
    private int balance;
}

