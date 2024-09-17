package com.ssafy.eggmoney.user.config;

import com.ssafy.eggmoney.user.service.AuthService;

public class JwtAuthenticationFilter {
    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, AuthService authService) {
    }
}
