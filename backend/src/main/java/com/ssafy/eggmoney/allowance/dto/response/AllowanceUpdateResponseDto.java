package com.ssafy.eggmoney.allowance.dto.response;


import com.ssafy.eggmoney.allowance.entity.Allowance;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AllowanceUpdateResponseDto {
    private Long userId;
    private int price;
    private String allowancePeriod;
    private int allowanceDay;

    public AllowanceUpdateResponseDto(Allowance allowance){
        if (allowance == null) {
            throw new IllegalArgumentException("Allowance Object:null");
        }
        this.userId = allowance.getChild().getId();
        this.price = allowance.getPrice();
        this.allowancePeriod = allowance.getAllowancePeriod().name();
        this.allowanceDay = allowance.getAllowanceDay();
    }
}
