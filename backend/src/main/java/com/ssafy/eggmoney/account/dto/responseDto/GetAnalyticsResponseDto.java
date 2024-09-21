package com.ssafy.eggmoney.account.dto.responseDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetAnalyticsResponseDto {
//    메인계좌 보유액
    int mainAccountBalance;
//    주식 현재평가액
    int stock;
//    적금 누적액
    int savings;
//    예금액
    int deposit;
}
