package com.ssafy.eggmoney.deposit.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class depositProductDto {
    private long id;
    private double rate;
    private int date;
}
