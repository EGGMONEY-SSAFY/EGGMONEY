package com.ssafy.eggmoney.loan.dto.response;

import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class LoanLogListResponseDto {
    private int id;
    private LocalDateTime createdAt;
    private int repayment;
    private int balance;
}
