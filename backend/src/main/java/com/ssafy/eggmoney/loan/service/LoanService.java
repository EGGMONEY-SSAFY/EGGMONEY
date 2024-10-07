package com.ssafy.eggmoney.loan.service;

import com.ssafy.eggmoney.loan.dto.request.LoanCreateRequestDto;
import com.ssafy.eggmoney.loan.dto.request.LoanEvaluationRequestDto;
import com.ssafy.eggmoney.loan.dto.response.LoanDetailResponseDto;
import com.ssafy.eggmoney.loan.dto.response.LoanLogListResponseDto;
import com.ssafy.eggmoney.loan.dto.response.LoanPrivateListResponseDto;
import com.ssafy.eggmoney.user.entity.User;

import java.util.List;

public interface LoanService {
    void createLoan(LoanCreateRequestDto requestDto, User user);

    List<LoanPrivateListResponseDto> getPrivateLoans(User user);

    LoanDetailResponseDto getDetailLoan(long loanId);

    void loanEvaluation(long loanId, LoanEvaluationRequestDto requestDto, User user);

    void sendRepayment(long loanId);

    List<LoanLogListResponseDto> getLoanLogs(long loanId);

    void expiredRepayment(Long loanId);

    List<Long> checkingExpired();
}
