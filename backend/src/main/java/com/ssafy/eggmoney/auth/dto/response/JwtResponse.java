package com.ssafy.eggmoney.auth.dto.response;



public class JwtResponse {
    private String accessToken;
    private String refreshToken;

    public JwtResponse(String accessToken, String refreshToken){
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
