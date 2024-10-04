package com.ssafy.eggmoney.auth.controller;

import com.ssafy.eggmoney.auth.dto.response.TokenResponse;
import com.ssafy.eggmoney.auth.service.KakaoAuthService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/kakao")
public class KakaoAuthController {

    private static final Logger logger = LoggerFactory.getLogger(KakaoAuthController.class);

    @Value("${kakao.client.id}")
    private String kakaoClientId;

    @Value("${kakao.redirect.uri}")
    private String kakaoRedirectUri;

    @Value("${kakao.redirect.logout}")
    private String KAKAO_LOGOUT_URL;

    @Autowired
    private KakaoAuthService kakaoService;

    @GetMapping("/login")
    public Mono<ResponseEntity<Void>> kakaoLogin() {

//        String kakaoAuthUrl = kakaoService.getKakaoAuthUrl().block();  // URL을 가져오는 부분
//        ResponseEntity<Void> response = ResponseEntity.status(HttpStatus.FOUND)
//                .location(URI.create(kakaoAuthUrl))  // 리다이렉트 URL 설정
//                .build();
        logger.info("Kakao login request received");  // 로그 추가
        return kakaoService.getKakaoAuthUrl()
                .map(kakaoAuthUrl -> ResponseEntity.status(HttpStatus.FOUND)
                        .location(URI.create(kakaoAuthUrl))
                        .build());
    }


//    public ResponseEntity<?> callbakc(@RequestParam("code") String code){
//
//        return new ResponseEntity<>(HttpStatus.OK);
//    })
@GetMapping("/callback")
public Mono<ResponseEntity<TokenResponse>> kakaoCallback(@RequestParam("code") String code) {
//    System.out.println("callback with :" + code);
//
//    return kakaoService.handleUserLogin(code)
//            .map(result -> {
//                String redirectUrl = result.get("redirectUrl");
//                return ResponseEntity.status(HttpStatus.FOUND)
//                        .location(URI.create(redirectUrl))
//                        .build();
//            });
    return kakaoService.handleUserLogin(code)
            .map(tokens -> ResponseEntity.ok(tokens));
}

//@PostMapping("/logout")
//public Mono<ResponseEntity<Void>> logout(@RequestHeader("Authorization") String accessToken){
//        return kakaoService.logout(accessToken)
//                .thenReturn(ResponseEntity.status(HttpStatus.FOUND)
//                        .location(URI.create(KAKAO_LOGOUT_URL))  // 로그아웃 후 프론트엔드 로그인 페이지로 리다이렉트
//                        .build());
//}

    @GetMapping("/logout")
    public Mono<ResponseEntity<Void>> kakaoLogout(){

        String logoutUrl = UriComponentsBuilder
                .fromHttpUrl("https://kauth.kakao.com/oauth/logout")
                .queryParam("client_id", kakaoClientId)
                .queryParam("logout_redirect_uri", KAKAO_LOGOUT_URL)
                .toUriString();
        return Mono.just(ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(logoutUrl))
                .build());
    }
}
