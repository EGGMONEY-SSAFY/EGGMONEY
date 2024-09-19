package com.ssafy.eggmoney.loan.dto.response;

import com.ssafy.eggmoney.loan.entity.LoanType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class LoanDetailResponseDto {
    LocalDateTime createdAt;
    LocalDateTime expirationDate;
    int loanAmount;
    int balance;
    String loanReason;
    String refuseReason;
    Double loanRate;
    LoanType loanType;
}
