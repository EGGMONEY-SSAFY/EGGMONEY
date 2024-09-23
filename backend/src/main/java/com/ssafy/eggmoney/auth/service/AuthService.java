package com.ssafy.eggmoney.auth.service;

import com.ssafy.eggmoney.auth.dto.response.JwtResponse;
import com.ssafy.eggmoney.auth.dto.request.LoginRequest;
import com.ssafy.eggmoney.auth.dto.request.RefreshTokenRequest;
import com.ssafy.eggmoney.user.entity.User;
import com.ssafy.eggmoney.user.repository.UserRepository;
import com.ssafy.eggmoney.auth.config.JwtTokenProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

//    private final UserRepository userRepository;
//    private final JwtTokenProvider jwtTokenProvider;
//    private final UserDetailsService userDetailsService;
//
//    public AuthService(UserRepository userRepository, JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
//        this.userRepository = userRepository;
//        this.jwtTokenProvider = jwtTokenProvider;
//        this.userDetailsService = userDetailsService;
//    }
//
//    public JwtResponse login(LoginRequest loginRequest) {
//        User user = userRepository.findByEmail(loginRequest.getEmail())
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        // 비밀번호 검증 로직 추가 필요
//
//        String accessToken = jwtTokenProvider.createAccessToken(user.getEmail());
//        String refreshToken = jwtTokenProvider.createRefreshToken(user.getEmail());
//
//        return new JwtResponse(accessToken, refreshToken);
//    }
//
//    public JwtResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
//        String username = jwtTokenProvider.getUsername(refreshTokenRequest.getRefreshToken());
//        User user = userRepository.findByEmail(username)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        String newAccessToken = jwtTokenProvider.createAccessToken(user.getEmail());
//
//        return new JwtResponse(newAccessToken, refreshTokenRequest.getRefreshToken());
//    }
//
//    public Authentication getAuthentication(String token) {
//        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtTokenProvider.getUsername(token));
//        return new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
//    }
}
