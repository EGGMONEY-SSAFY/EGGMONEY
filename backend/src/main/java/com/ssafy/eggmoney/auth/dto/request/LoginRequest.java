package com.ssafy.eggmoney.auth.dto.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    private String email;
    private String password;//비밀번호 필드 추가


}
