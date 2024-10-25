package com.ssafy.eggmoney.deposit.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DeleteDepositResponseDto {
    long depositId;
    int  depositMoney;
    double interestMoney;
    int expiredMoney;
}
