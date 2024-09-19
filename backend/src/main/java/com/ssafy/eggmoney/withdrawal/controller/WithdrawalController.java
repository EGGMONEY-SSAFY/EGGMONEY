package com.ssafy.eggmoney.withdrawal.controller;

import com.ssafy.eggmoney.withdrawal.dto.request.CreateWithdrawalRequestDto;
import com.ssafy.eggmoney.withdrawal.dto.request.GetWithdrawalRequestDto;
import com.ssafy.eggmoney.withdrawal.dto.request.JudgeWithdrawalRequestDto;
import com.ssafy.eggmoney.withdrawal.dto.response.GetWithdrawalResponseDto;
import com.ssafy.eggmoney.withdrawal.service.WithdrawalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/asset/withdrawal")
public class WithdrawalController {
    private WithdrawalService withDrawalService;

//    출금 요청 로그 조회
    @GetMapping("/log")
    public List<GetWithdrawalResponseDto> getWithdrawalLogs(@RequestBody GetWithdrawalRequestDto dto){
        return withDrawalService.getWithdrawalLogs(dto);
    }

//    출금 요청 심사
    @PostMapping("/judge/{withdrawal_id}")
    public void judgeWithdrawal(@PathVariable("withdrawal_id")Long Wid, @RequestBody JudgeWithdrawalRequestDto dto){
        withDrawalService.judgeWithdrawal(Wid, dto);
    }

//    출금 요청 생성
    @PostMapping("/create")
    public void createWithdrawal(@RequestBody CreateWithdrawalRequestDto dto){
        withDrawalService.createWithdrawal(dto);
    }

}
