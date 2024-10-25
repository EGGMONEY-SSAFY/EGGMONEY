package com.ssafy.eggmoney.allowance.dto.response;

import com.ssafy.eggmoney.allowance.entity.Allowance;
import lombok.Getter;

@Getter
public class AllowanceCreateResponseDto {
    private Long id;
    private int price;
    private int allowanceDay;

    public AllowanceCreateResponseDto(Allowance allowance){
        this.id = allowance.getId();
        this.price = allowance.getPrice();
        this.allowanceDay = allowance.getAllowanceDay();
    }
}
