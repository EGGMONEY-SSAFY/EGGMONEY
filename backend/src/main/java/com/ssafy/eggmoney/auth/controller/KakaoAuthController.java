package com.ssafy.eggmoney.auth.controller;

import com.ssafy.eggmoney.auth.dto.response.TokenResponse;
import com.ssafy.eggmoney.auth.service.KakaoAuthService;

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

@CrossOrigin(origins="http://localhost:5173")
@RestController
@RequestMapping("/api/kakao")
public class KakaoAuthController {

    @Value("${kakao.client.id}")
    private String kakaoClientId;

    @Value("${kakao.redirect.uri}")
    private String kakaoRedirectUri;

    @Autowired
    private KakaoAuthService kakaoService;

    @GetMapping("/login")
    public Mono<ResponseEntity<Void>> kakaoLogin() {

//        String kakaoAuthUrl = kakaoService.getKakaoAuthUrl().block();  // URL을 가져오는 부분
//        ResponseEntity<Void> response = ResponseEntity.status(HttpStatus.FOUND)
//                .location(URI.create(kakaoAuthUrl))  // 리다이렉트 URL 설정
//                .build();
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


}
