package com.ssafy.eggmoney.common.exception;


import org.springframework.web.ErrorResponse;

public enum ErrorType {
//-------- 공통 관련 -----------------
    IS_ILLEGAL_REQUEST("올바른 요청이 아닙니다"),
//-------- 회원 관련 -----------------
    NOT_FOUND_UESR("검색한 유저가 없습니다"),
//-------- 가족 관련 -----------------
    NOT_FOUND_FAMILY("검색된 가족이 없습니다"),
    USER_NOT_PRESENT("해당 유저는 가족 구성원이 아닙니다"),
//-------- 자산 관련 -----------------
    NOT_FOUND_ACCOUNT("일치하는 계좌가 없습니다"),
    NOT_FOUND_SAVINGS("일치하는 적금이 없습니다"),
    NOT_FOUND_LOAN("일치하는 대출이 없습니다"),
    NOT_FOUND_DEPOSIT("일치하는 예금이 없습니다"),
//-------- 출금 관련 -----------------
    NOT_FOUND_WITHDRAWAL("일치하는 출금 요청이 없습니다"),
    NOT_FOUND_CHILD("일치하는 자녀가 없습니다"),
    NOT_FOUND_PARENT("일치하는 부모가 없습니다"),
    NOT_ENOUGH_MONEY("계좌에 돈이 충분하지 않습니다"),
    NOT_PRESENT_USER("가족의 대표만 심사할 수 있습니다"),
    WITH_ALREADY_JUDGED("가족의 대표만 심사할 수 있습니다"),
    API_NETWORK_ERROR("공동망 API 처리 과정에서 에러가 발생했습니다"),
//    NOT_PRESENT_USER("가족의 대표만 심사할 수 있습니다.");

//-------- 금융 관련 -----------------
    NOT_CREATED_ROLE("계좌를 생성할 권한이 없습니다"),
    NOT_CREATED_ACCOUNT("이미 계좌가 존재합니다"),
    NOT_FOUND_PRODUCT("상품이 존재하지 않습니다"),
    EXCEED_MAX_PRICE("최대 금액을 초과합니다"),
    ALREADY_PAY_SAVINGS("이미 적금을 모두 납부하였습니다"),
    NOT_JUDGE_ROLE("요청을 심사할 권한이 없습니다")
    ;
    private String msg;

    ErrorType(String msg) {
        this.msg = msg;
    }

    public String toString(){
        return this.msg;
    }

}
