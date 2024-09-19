package com.ssafy.eggmoney.withdrawal.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetWithdrawalRequestDto {
    Long userId;
}
