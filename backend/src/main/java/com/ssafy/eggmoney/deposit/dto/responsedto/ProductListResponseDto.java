package com.ssafy.eggmoney.deposit.dto.responsedto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ProductListResponseDto {
    private Long productId;
    private int depositDate;
    private Double depositRate;
}
