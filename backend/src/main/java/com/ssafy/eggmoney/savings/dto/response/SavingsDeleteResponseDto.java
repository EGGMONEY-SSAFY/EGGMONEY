package com.ssafy.eggmoney.savings.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SavingsDeleteResponseDto {
    long savingsId;
    double interestMoney;
    int paymentMoney;
    int paymentDate;
    int expiredMoney;

}
