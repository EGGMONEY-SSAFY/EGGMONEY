package com.ssafy.eggmoney.account.controller;

import com.ssafy.eggmoney.account.dto.responseDto.GetAccountResponseDto;
import com.ssafy.eggmoney.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/asset/main-account")
public class AccountController {
    private final AccountService accountService;

//    메인계좌 조회
    @GetMapping("")
    public GetAccountResponseDto getAccount(){
        return accountService.getAccount();
    }
}
