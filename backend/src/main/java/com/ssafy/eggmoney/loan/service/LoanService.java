package com.ssafy.eggmoney.loan.service;

import com.ssafy.eggmoney.loan.dto.request.LoanCreateRequestDto;
import com.ssafy.eggmoney.loan.entity.Loan;

public interface LoanService {
    void createLoan(LoanCreateRequestDto requestDto);
}
