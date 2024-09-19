package com.ssafy.eggmoney.loan.service;

import com.ssafy.eggmoney.loan.dto.request.LoanCreateRequestDto;
import com.ssafy.eggmoney.loan.dto.request.LoanEvaluationRequestDto;
import com.ssafy.eggmoney.loan.dto.response.LoanDetailResponseDto;
import com.ssafy.eggmoney.loan.dto.response.LoanPrivateListResponseDto;

import java.util.List;

public interface LoanService {
    void createLoan(LoanCreateRequestDto requestDto);

    List<LoanPrivateListResponseDto> getPrivateLoans(long userId);

    LoanDetailResponseDto getDetailLoan(long loanId);

    void loanEvaluation(long loanId, LoanEvaluationRequestDto requestDto);

    void sendRepayment(long loanId);
}
