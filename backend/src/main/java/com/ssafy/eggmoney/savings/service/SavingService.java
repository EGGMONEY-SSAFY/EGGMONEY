package com.ssafy.eggmoney.savings.service;

import com.ssafy.eggmoney.savings.dto.request.SavingsCreateRequestDto;
import com.ssafy.eggmoney.savings.dto.response.SavingsLogResponseDto;
import com.ssafy.eggmoney.savings.dto.response.SavingsProductListResponseDto;
import com.ssafy.eggmoney.savings.dto.response.SavingsResponseDto;

import java.util.List;

public interface SavingService {
    List<SavingsProductListResponseDto> getSavingProducts();
    void createSaving(SavingsCreateRequestDto savingsCreateRequestDto);
    SavingsResponseDto getSavings(Long userId);
    void sendSavings(Long userId);
    List<SavingsLogResponseDto> getSavingsLogs(Long savingsId);
}
