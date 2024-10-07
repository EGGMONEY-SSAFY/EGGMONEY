package com.ssafy.eggmoney.loan.controller;

import com.ssafy.eggmoney.auth.service.KakaoAuthService;

import com.ssafy.eggmoney.loan.dto.request.LoanCreateRequestDto;
import com.ssafy.eggmoney.loan.dto.request.LoanEvaluationRequestDto;
import com.ssafy.eggmoney.loan.dto.response.LoanDetailResponseDto;
import com.ssafy.eggmoney.loan.dto.response.LoanLogListResponseDto;
import com.ssafy.eggmoney.loan.dto.response.LoanPrivateListResponseDto;
import com.ssafy.eggmoney.loan.service.LoanService;
import com.ssafy.eggmoney.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/api/v1/fin/loan")
@RestController
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;
    private final KakaoAuthService kakaoAuthService;

    /**
     * 대출생성
     * @param requestDto
     * return
     * */
    @PostMapping("/create")
    public ResponseEntity<?> createLoan(@RequestHeader(value = "Authorization") String token,
                                        @RequestBody LoanCreateRequestDto requestDto) {
        User user = kakaoAuthService.verifyKakaoToken(token);
        loanService.createLoan(requestDto, user);
        return ResponseEntity.ok().build();
    }

    /**
     * 개인 대출 조회
     * return List<LoanPrivateListResponseDto>
     * */
    @PostMapping("")
    public ResponseEntity<List<LoanPrivateListResponseDto>> getPrivateLoans(@RequestHeader(value = "Authorization") String token) {
        User user = kakaoAuthService.verifyKakaoToken(token);
        List<LoanPrivateListResponseDto> result = loanService.getPrivateLoans(user);

        return ResponseEntity.ok().body(result);
    }

    /**
     * 대출상세조회
     * @param loanId
     * return LoanDetailResponseDto
     * */
    @GetMapping("/detail/{loanId}")
    public ResponseEntity<LoanDetailResponseDto> getDetailLoan(@RequestHeader(value = "Authorization") String token,
            @PathVariable long loanId){
        LoanDetailResponseDto result = loanService.getDetailLoan(loanId);

        return ResponseEntity.ok().body(result);
    }

    /**
     * 대출심사
     * @param loanId, requestDto
     * return
     * */
    @PostMapping("/judge/{loanId}")
    public ResponseEntity<?> evaluation(@RequestHeader(value = "Authorization") String token, @PathVariable long loanId, @RequestBody LoanEvaluationRequestDto requestDto ) {
        User user = kakaoAuthService.verifyKakaoToken(token);
        loanService.loanEvaluation(loanId, requestDto, user);

        return ResponseEntity.ok().build();
    }

    /**
     * 대출금상환
     * @param loanId
     * return
     * */
    @PostMapping("/send/{loanId}")
    public ResponseEntity<?> repayment(@RequestHeader(value = "Authorization") String token, @PathVariable long loanId) {
        log.info("아아");
        loanService.sendRepayment(loanId);

        return ResponseEntity.ok().build();
    }

    /**
     * 대출금 납입 로그 조회
     * @param loanId
     * return List<LoanLogListResponseDto>
     * */
    @GetMapping("/log/{loanId}")
    public ResponseEntity<List<LoanLogListResponseDto>> getLoanLogs(@RequestHeader(value = "Authorization") String token, @PathVariable long loanId) {
        List<LoanLogListResponseDto> result = loanService.getLoanLogs(loanId);
        return ResponseEntity.ok().body(result);
    }


}
