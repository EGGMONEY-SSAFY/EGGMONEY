package com.ssafy.eggmoney.user.dto.reqeust;

import lombok.Getter;

@Getter
public class InvestmentRatioRequest {
    private Long childId;
    private int ratio;
}
