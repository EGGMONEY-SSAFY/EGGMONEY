package com.ssafy.eggmoney.loan.dto.response;

import com.ssafy.eggmoney.loan.entity.LoanStatus;
import com.ssafy.eggmoney.loan.entity.LoanType;
import com.ssafy.eggmoney.user.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class LoanPrivateListResponseDto {
    long loanId;
    String userName;
    LoanType loanType;
    LoanStatus loanStatus;
    int loanAmount;
    int loanDate;
    int balance;
    String loanReason;
    String refuseReason;
    Double loanRate;
    LocalDateTime expirationDate;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
