package com.ssafy.eggmoney.auth.controller;

import com.ssafy.eggmoney.auth.service.KakaoAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/kakao")
public class KakaoAuthController {

    @Value("${kakao.client.id}")
    private String kakaoClientId;

    @Value("${kakao.redirect.uri}")
    private String kakaoRedirectUri;

    @Autowired
    private KakaoAuthService kakaoService;

    @GetMapping("/login")
    public ResponseEntity<Void> kakaoLogin() {
        // 카카오 로그인 URL로 리다이렉트
        String kakaoAuthUrl = kakaoService.getKakaoAuthUrl();
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(kakaoAuthUrl)).build();
    }

    @GetMapping("/callback")
    public ResponseEntity<String> kakaoCallback(@RequestParam String code) {
        System.out.println("Received Kakao Callback with code: " + code);

        try {
            String accessToken = kakaoService.getAccessToken(code);
            System.out.println("Kakao Token Response: " + accessToken);

            kakaoService.handleUserLogin(accessToken);

            return ResponseEntity.ok("Successfully authenticated with Kakao");
        } catch (Exception e) {
            System.err.println("Error occurred while calling Kakao API: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while calling Kakao API");
        }
    }
}
