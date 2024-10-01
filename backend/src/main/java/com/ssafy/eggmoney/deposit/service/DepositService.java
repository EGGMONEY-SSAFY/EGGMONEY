package com.ssafy.eggmoney.deposit.service;

import com.ssafy.eggmoney.deposit.dto.request.DepositCreateRequestDto;
import com.ssafy.eggmoney.deposit.dto.response.DeleteDepositResponseDto;
import com.ssafy.eggmoney.deposit.dto.response.DepositProductListResponseDto;
import com.ssafy.eggmoney.deposit.dto.response.DepositResponseDto;

import java.util.List;

public interface DepositService {
    void createDeposit(DepositCreateRequestDto requestDto);
    DepositResponseDto getDeposits(long id);
    List<DepositProductListResponseDto> getDepositProducts();

    DeleteDepositResponseDto deleteDeposit(long depositId);
    List<Long> checkExpiredDeposit();
}
