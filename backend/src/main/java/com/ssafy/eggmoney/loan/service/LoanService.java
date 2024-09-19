package com.ssafy.eggmoney.loan.service;

import com.ssafy.eggmoney.loan.dto.request.LoanCreateRequestDto;
import com.ssafy.eggmoney.loan.dto.response.LoanDetailResponseDto;
import com.ssafy.eggmoney.loan.dto.response.LoanPrivateListResponseDto;
import com.ssafy.eggmoney.loan.entity.Loan;

import java.util.List;

public interface LoanService {
    void createLoan(LoanCreateRequestDto requestDto);

    List<LoanPrivateListResponseDto> getPrivateLoans(long userId);

    LoanDetailResponseDto getDetailLoan(long loanId);
}
