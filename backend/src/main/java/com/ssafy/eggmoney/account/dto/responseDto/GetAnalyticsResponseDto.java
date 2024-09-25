package com.ssafy.eggmoney.account.dto.responseDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetAnalyticsResponseDto {
//    메인계좌 보유액
    Integer mainAccountBalance;
//    주식 현재평가액
    Integer stock;
//    적금 누적액
    Integer savings;
//    대출액
    Integer loan;
//    예금액
    Integer deposit;
}
