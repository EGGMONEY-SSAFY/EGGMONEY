package com.ssafy.eggmoney.withdrawal.controller;

import com.ssafy.eggmoney.auth.service.KakaoAuthService;
import com.ssafy.eggmoney.user.entity.User;
import com.ssafy.eggmoney.withdrawal.dto.request.CreateWithdrawalRequestDto;
import com.ssafy.eggmoney.withdrawal.dto.request.JudgeWithdrawalRequestDto;
import com.ssafy.eggmoney.withdrawal.dto.request.WithdrawalRequestDto;
import com.ssafy.eggmoney.withdrawal.dto.response.GetWithdrawalResponseDto;
import com.ssafy.eggmoney.withdrawal.service.WithdrawalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/asset/withdrawal")
public class WithdrawalController {
    private final WithdrawalService withdrawalService;
    private final KakaoAuthService kakaoAuthService;

//    출금 요청 로그 조회
    @PostMapping("/log")
    public List<GetWithdrawalResponseDto> getWithdrawalLogs(@RequestHeader(value = "Authorization") String token,
                                                            @RequestBody WithdrawalRequestDto dto){
        User user = kakaoAuthService.verifyKakaoToken(token);
        return withdrawalService.getWithdrawalLogs(dto.getUserId());
    }

//    출금 요청 심사
    @PostMapping("/judge/{withdrawal_id}")
    public void judgeWithdrawal(@PathVariable("withdrawal_id")Long Wid, @RequestBody JudgeWithdrawalRequestDto dto){
        withdrawalService.judgeWithdrawal(Wid, dto);
    }

//    출금 요청 생성
    @PostMapping("/create")
    public void createWithdrawal(@RequestHeader(value = "Authorization") String token,
                                @RequestBody CreateWithdrawalRequestDto dto) {
        User user = kakaoAuthService.verifyKakaoToken(token);
        withdrawalService.createWithdrawal(user.getId(), dto);
    }

}
