package com.ssafy.eggmoney.user.dto.reqeust;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateUserRequestDto {
    private String name;
    private String bank;
    private String realAccount;
    private String simplePwd;
}
