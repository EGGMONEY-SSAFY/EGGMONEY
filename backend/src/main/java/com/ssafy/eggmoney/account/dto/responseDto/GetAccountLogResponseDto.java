package com.ssafy.eggmoney.account.dto.responseDto;

import com.ssafy.eggmoney.account.entity.Account;
import com.ssafy.eggmoney.account.entity.AccountLogType;
import com.ssafy.eggmoney.user.dto.response.GetUserResponseDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

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
