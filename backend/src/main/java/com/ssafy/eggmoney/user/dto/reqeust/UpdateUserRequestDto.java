package com.ssafy.eggmoney.user.dto.reqeust;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UpdateUserRequestDto {
    private String name;
    private String bank;
    private String realAccount;
    private String simplePwd;
    private String role;
    //private boolean authStatus;  // 계좌 인증 상태
}
