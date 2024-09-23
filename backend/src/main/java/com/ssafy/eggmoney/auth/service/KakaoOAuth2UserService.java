package com.ssafy.eggmoney.auth.service;

import com.ssafy.eggmoney.auth.dto.response.TokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.Map;

@Service
public class KakaoOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String kakaoClientId;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String kakaoRedirectUri;
    private final WebClient webClient;

    public KakaoOAuth2UserService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        String accessToken = userRequest.getAccessToken().getTokenValue();

        // 카카오 사용자 정보 요청
        Map<String, Object> attributes = webClient.get()
                .uri("https://kapi.kakao.com/v2/user/me")
                .headers(headers -> headers.setBearerAuth(accessToken))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                })
                .block();

        // OAuth2User 객체 생성
        return new DefaultOAuth2User(
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")),
                attributes,
                kakaoClientId
                //userNameAttributeName
        );
    }
    public TokenResponse getAccessToken(String code){
        return webClient.post()
                .uri("https://kauth.kakao.com/oauth/token")
                .body(BodyInserters.fromFormData("grant_type", "authorization_code")
                        .with("client_id", kakaoClientId)
                        .with("redirect_uri", kakaoRedirectUri)
                        .with("code", code))
                .retrieve()
                .bodyToMono(TokenResponse.class)
                .block();
    }
    public TokenResponse refreshAccessToken(String refreshToken){
        return webClient.post()
                .uri("https://kauth.kakao.com/oauth/token")
                .body(BodyInserters.fromFormData("grant_type","refresh_token")
                    .with("client_id",kakaoClientId)
                    .with("refresh_token", refreshToken))
                .retrieve()
                .bodyToMono(TokenResponse.class)
                .block();
    }

    public void logout(String accessToken) {
        webClient.post()
                .uri("https://kapi.kakao.com/v1/user/logout")
                .headers(headers -> headers.setBearerAuth(accessToken))
                .retrieve()
                .bodyToMono(void.class)
                .block();
    }
//    private OAuth2User fetchUserInfoFromProvider(OAuth2UserRequest userRequest){
//        return null;
//    }
}
