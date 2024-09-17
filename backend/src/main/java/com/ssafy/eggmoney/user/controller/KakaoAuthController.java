package com.ssafy.eggmoney.user.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@CrossOrigin(origins = "*")
public class KakaoAuthController {

    @Value("${kakao.client.id}")
    private String kakaoClientId;

//    @Value("${kakao.client.secret}")
//    private String KakaoClientIdSecret;

    @Value("${kakao.redirect.uri}")
    private String kakaoRedirectUri;

    @GetMapping("/kakao-callback")
    public ResponseEntity<String> kakaoCallback(@RequestParam String code){
        String tokenUrl = "https://kauth.kakao.com/oauth/token";
        String requestUrl = tokenUrl+"?grant_type=authorization_code&client_id="+kakaoClientId
                + "&redirect_uri="+kakaoRedirectUri +"&code="+code;

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.postForObject(requestUrl, null, String.class);

        // 로그 출력
        System.out.println("Kakao Token Response: " + response);
        //return null;
        return ResponseEntity.ok(response);
    }
}
