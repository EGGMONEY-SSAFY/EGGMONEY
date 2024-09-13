package com.ssafy.eggmoney.savings.service;

import com.ssafy.eggmoney.savings.dto.responseDto.SavingsProductListResponseDto;

import java.util.List;

public interface SavingService {
    List<SavingsProductListResponseDto> getSavingProducts();
}
