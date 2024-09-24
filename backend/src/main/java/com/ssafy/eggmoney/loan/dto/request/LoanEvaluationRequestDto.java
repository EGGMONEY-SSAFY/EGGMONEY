package com.ssafy.eggmoney.loan.dto.request;

import com.ssafy.eggmoney.loan.entity.LoanStatus;
import lombok.Getter;

@Getter
public class LoanEvaluationRequestDto {
    private LoanStatus loanStatus;
    private String refuseReason;
    private double loanRate;
}
