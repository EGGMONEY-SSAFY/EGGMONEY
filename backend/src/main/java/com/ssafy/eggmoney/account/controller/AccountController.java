package com.ssafy.eggmoney.account.controller;

import com.ssafy.eggmoney.account.dto.responseDto.GetAccountLogResponseDto;
import com.ssafy.eggmoney.account.dto.responseDto.GetAccountResponseDto;
import com.ssafy.eggmoney.account.dto.responseDto.GetAnalyticsResponseDto;
import com.ssafy.eggmoney.account.entity.AccountLog;
import com.ssafy.eggmoney.account.service.AccountLogService;
import com.ssafy.eggmoney.account.service.AccountService;
import com.ssafy.eggmoney.auth.service.KakaoAuthService;
import com.ssafy.eggmoney.user.entity.User;
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
    private final KakaoAuthService kakaoAuthService;
//    메인계좌 조회
    @GetMapping("/main-account")
    public GetAccountResponseDto getAccount(@RequestHeader(value = "Authorization") String token){
        User user = kakaoAuthService.verifyKakaoToken(token);
        return accountService.getAccount(user.getId());
    }

//    메인 계좌 로그 조회
    @GetMapping("/main-account/log")
    public Page<GetAccountLogResponseDto> getAccountLogs(
            @RequestHeader(value = "Authorization") String token,
            @RequestParam("page") int page,
            @RequestParam("size") int size) {
        Pageable pageable = PageRequest.of(page, size);
        User user = kakaoAuthService.verifyKakaoToken(token);
        return accountLogService.getAccountLogs(user.getId(), pageable);
    }

//    메인계좌 로그 조회 ( 3개월 기준 )
    @GetMapping("/main-account/{month}/log")
    public List<GetAccountLogResponseDto> get3MAccountLogs(@RequestHeader(value = "Authorization") String token,
                                                           @PathVariable("month") Integer month)
    {
        User user = kakaoAuthService.verifyKakaoToken(token);
        return accountLogService.get3MAccountLogs(user.getId(), month);
    }


//    자산 분석 ( 현재보유금, 예금, 적금, 주식 현황 )
    @GetMapping("/analytics")
    public GetAnalyticsResponseDto getAnalytics(@RequestHeader(value = "Authorization") String token){
        User user = kakaoAuthService.verifyKakaoToken(token);
        return accountService.getAnalytics(user.getId());
    }
}
