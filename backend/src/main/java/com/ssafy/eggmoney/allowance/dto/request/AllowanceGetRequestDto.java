package com.ssafy.eggmoney.allowance.dto.request;

import com.ssafy.eggmoney.allowance.entity.Allowance;
import lombok.Getter;

@Getter
public class AllowanceGetRequestDto {
    private Long id;
    private String name;
    private int price;
    private String allowancePeriod;
    private int allowanceDay;

    public AllowanceGetRequestDto(Allowance allowance){
        this.id = allowance.getId();
        this.name = allowance.getChild().getName();
        this.price = allowance.getPrice();
        this.allowancePeriod = allowance.getAllowancePeriod().name();
        this.allowanceDay = allowance.getAllowanceDay();
    }

}
