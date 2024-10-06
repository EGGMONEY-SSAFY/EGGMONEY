package com.ssafy.eggmoney.account.dto.response;

import com.ssafy.eggmoney.account.entity.AccountLogType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetAccountLogResponseDto {
    private Long accountId;
    private int currentBalance;
    private int tradePrice;
    private AccountLogType tradeTarget;
    private LocalDateTime createdAt;

    @Builder
    private GetAccountLogResponseDto(Long accountId, int currentBalance, int tradePrice, AccountLogType tradeTarget, LocalDateTime createdAt) {
        this.accountId = accountId;
        this.currentBalance = currentBalance;
        this.tradePrice = tradePrice;
        this.tradeTarget = tradeTarget;
        this.createdAt = createdAt;
    }
}
