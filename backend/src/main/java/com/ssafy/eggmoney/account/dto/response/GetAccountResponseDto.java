package com.ssafy.eggmoney.account.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetAccountResponseDto {
    Long userId;
    int balance;
}
