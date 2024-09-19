package com.ssafy.eggmoney.loan.dto.request;

import com.ssafy.eggmoney.loan.entity.LoanType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoanCreateRequestDto {
    long userId;
    LoanType loanType;
    int loanAmount;
    int loanDate;
    String loanReason;
}
