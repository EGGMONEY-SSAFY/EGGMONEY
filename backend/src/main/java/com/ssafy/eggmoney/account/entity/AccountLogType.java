package com.ssafy.eggmoney.account.entity;

import lombok.Getter;

@Getter
public enum AccountLogType {
//    아래 부분에 로그 유형 추가
//    자산
    WITHDRAWL_APPLY("출금 완료"),
    SAVINGS_PAY("적금 납입"),
    DEPOSIT_PAY("예금 납입"),
    DEPOSIT_EXPIRE("예금 만기"),
    SAVINGS_EXPIRE("적금 만기"),
    ALLOWANCE_GET("용돈 수령"),
    LOAN_GET("대출 수령"),
    LOAN_PAY("대출 상환"),
    STOCK_BUY("주식 구입"),
    STOCK_SELL("주식 판매"),
    STOCK_BOOK_BUY("매수 예약");
//    STOCK_BOOK_SELL("매도 예약"),
//    STOCK

//    금융
//    증권
//    전체

    private String logType;

    AccountLogType(String logType) {
        this.logType = logType;
    }
}
