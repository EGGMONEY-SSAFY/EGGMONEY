package com.ssafy.eggmoney.loan.entity;

public enum LoanType {

    /*
    * 원리금균등상환: 대출 기간 동안 매달 원금과 이자를 합친 금액이 일정하게 상환되는 방식
    * 만기일시상환:
    */

    EQUALR("원리금균등상환"), LUMPSUM("만기일시상환");
    final String value;

    LoanType(String value) {
        this.value = value;
    }
}