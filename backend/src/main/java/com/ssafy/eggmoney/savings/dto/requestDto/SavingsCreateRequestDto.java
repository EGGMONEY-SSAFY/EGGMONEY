package com.ssafy.eggmoney.savings.dto.requestDto;

import com.ssafy.eggmoney.savings.entity.SavingsProduct;
import lombok.Getter;

@Getter
public class SavingsCreateRequestDto {
    private long userId;
    private long savingsProductId;
    private int paymentDate;
    private int paymentMoney;
    private int price;


}
