package com.ssafy.eggmoney.auth.controller;

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

@CrossOrigin(origins="localhost:5173")
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
        String kakaoAuthUrl = kakaoService.getKakaoAuthUrl().block();  // URL을 가져오는 부분
        ResponseEntity<Void> response = ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(kakaoAuthUrl))  // 리다이렉트 URL 설정
                .build();

        // 요청 결과를 출력해보기
        System.out.println("Response: " + response);
        System.out.println("Location header: " + kakaoAuthUrl);
        return response;
    }

    @GetMapping("/callback")
    public ResponseEntity<?> callbakc(@RequestParam("code") String code){
        System.out.println("!");
        return new ResponseEntity<>(HttpStatus.OK);
    }
//    public Mono<ResponseEntity<Map<String,String>>> kakaoCallback() {
//        String code="i-Cqxp6gYhVxayIAcM-Zuvuo_yFq6aqJowaYDGMsfSJ4Xff2-uBAWwAAAAQKKiWPAAABkg5sWKAtjdRiIM79qQ";
//        System.out.println("Received Kakao Callback with code: " + code);
//
//        return kakaoService.handleUserLogin(code)
//                .map(user -> {
//                    Map<String, String> response = new HashMap<>();
//                    return ResponseEntity.ok(response);
//                })
//                .onErrorResume(e -> {
//                    System.err.println("Kakao API 에러: "+ e.getMessage());
//                    Map<String, String> errorResponse = new HashMap<>();
//                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse));
//                });
//    }

}
