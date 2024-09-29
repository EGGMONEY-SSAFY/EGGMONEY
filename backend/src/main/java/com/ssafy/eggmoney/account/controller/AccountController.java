package com.ssafy.eggmoney.account.controller;

import com.ssafy.eggmoney.account.dto.responseDto.GetAccountLogResponseDto;
import com.ssafy.eggmoney.account.dto.responseDto.GetAccountResponseDto;
import com.ssafy.eggmoney.account.dto.responseDto.GetAnalyticsResponseDto;
import com.ssafy.eggmoney.account.entity.AccountLog;
import com.ssafy.eggmoney.account.service.AccountLogService;
import com.ssafy.eggmoney.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/asset")
public class AccountController {
    private final AccountService accountService;
    private final AccountLogService accountLogService;

//    메인계좌 조회
    @GetMapping("/main-account/{userId}")
    public GetAccountResponseDto getAccount(@PathVariable("userId") Long userId){

        return accountService.getAccount(userId);
    }

//    메인 계좌 로그 조회
    @GetMapping("/main-account/{userId}/log")
    public Page<GetAccountLogResponseDto> getAccountLogs(
            @PathVariable("userId") Long userId,
            @RequestParam("page") int page,
            @RequestParam("size") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return accountLogService.getAccountLogs(userId, pageable);
    }

//    메인계좌 로그 조회 ( 3개월 기준 )
    @GetMapping("/main-account/{userId}/{month}/log")
    public List<GetAccountLogResponseDto> get3MAccountLogs(@PathVariable("userId") Long userId, @PathVariable("month") Integer month)
    {
        return accountLogService.get3MAccountLogs(userId, month);
    }


//    자산 분석 ( 현재보유금, 예금, 적금, 주식 현황 )
    @GetMapping("/analytics/{userId}")
    public GetAnalyticsResponseDto getAnalytics(@PathVariable("userId") Long userId){
        return accountService.getAnalytics(userId);
    }
}
