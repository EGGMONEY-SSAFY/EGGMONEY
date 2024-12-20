package com.ssafy.eggmoney.deposit.dto.response;

import com.ssafy.eggmoney.deposit.dto.DepositProductDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class DepositResponseDto {
    private Long depositId;
    private DepositProductDto depositProduct;
    private LocalDateTime createdAt;
    private LocalDateTime expireDate;
    private int depositMoney;
}
