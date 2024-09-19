package com.ssafy.eggmoney.loan.controller;

import com.ssafy.eggmoney.loan.dto.request.LoanCreateRequestDto;
import com.ssafy.eggmoney.loan.dto.response.LoanPrivateListResponseDto;
import com.ssafy.eggmoney.loan.entity.Loan;
import com.ssafy.eggmoney.loan.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/fin/loan")
@RestController
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @PostMapping("/create")
    public ResponseEntity<?> createLoan(@RequestBody LoanCreateRequestDto requestDto) {
        loanService.createLoan(requestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<LoanPrivateListResponseDto>> getPrivateLoans(@PathVariable long userId) {
        List<LoanPrivateListResponseDto> result = loanService.getPrivateLoans(userId);

        return ResponseEntity.ok().body(result);
    }
}
