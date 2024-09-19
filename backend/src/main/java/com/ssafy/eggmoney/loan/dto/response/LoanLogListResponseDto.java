package com.ssafy.eggmoney.loan.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class LoanLogListResponseDto {
    private long id;
    private LocalDateTime createdAt;
    private int repayment;
    private int balance;
}
