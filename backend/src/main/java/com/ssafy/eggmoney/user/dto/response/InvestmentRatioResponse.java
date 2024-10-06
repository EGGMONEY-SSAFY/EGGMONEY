package com.ssafy.eggmoney.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InvestmentRatioResponse {
    Long userId;
    String name;
    int ratio;
}