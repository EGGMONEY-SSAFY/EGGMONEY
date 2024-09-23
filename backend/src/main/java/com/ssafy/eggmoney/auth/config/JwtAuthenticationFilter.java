package com.ssafy.eggmoney.auth.config;

import com.ssafy.eggmoney.auth.service.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import java.io.IOException;


public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

//    private final JwtTokenProvider jwtTokenProvider;
//    private final AuthService authService;
//
//    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, AuthService authService) {
//        this.jwtTokenProvider = jwtTokenProvider;
//        this.authService = authService;
//    }
//
//
//    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//        String token = jwtTokenProvider.resolveToken(request);
//        if (token != null && jwtTokenProvider.validateToken(token)) {
//            SecurityContextHolder.getContext().setAuthentication(authService.getAuthentication(token));
//        }
//        chain.doFilter(request, response);
//    }
//
//    private boolean validateKakaoToken(String token){
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization","Bearer"+token);
//        HttpEntity<String> entity = new HttpEntity<>(headers);
//
//        try{
//            ResponseEntity<String> response = restTemplate.exchange(
//                    "http://kapi.kakao.com/v1/user/access_token_info",
//                    HttpMethod.GET,
//                    entity,
//                    String.class);
//            return response.getStatusCode().is2xxSuccessful();
//        } catch (Exception e){
//            return false;
//        }

//    }
}
