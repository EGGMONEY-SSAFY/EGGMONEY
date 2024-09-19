package com.ssafy.eggmoney.savings.dto.request;

import lombok.Getter;

@Getter
public class SavingsCreateRequestDto {
    private long userId;
    private long savingsProductId;
    private int paymentMoney;


}
