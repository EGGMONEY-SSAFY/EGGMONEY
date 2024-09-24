package com.ssafy.eggmoney.account.dto.responseDto;

import com.ssafy.eggmoney.account.entity.Account;
import com.ssafy.eggmoney.account.entity.AccountLogType;
import com.ssafy.eggmoney.user.dto.response.GetUserResponseDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
public class GetAccountLogResponseDto {
    private GetAccountResponseDto account;
    private int currentBalance;
    private int tradePrice;
    private AccountLogType tradeTarget;

    @Builder
    private GetAccountLogResponseDto(GetAccountResponseDto account, int currentBalance, int tradePrice, AccountLogType tradeTarget) {
        this.account = account;
        this.currentBalance = currentBalance;
        this.tradePrice = tradePrice;
        this.tradeTarget = tradeTarget;
    }
}
