package com.ssafy.eggmoney.auth.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.eggmoney.auth.dto.response.KakaoUserResponse;
import com.ssafy.eggmoney.auth.dto.response.TokenResponse;
import com.ssafy.eggmoney.user.entity.User;
import com.ssafy.eggmoney.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class KakaoAuthService {

    @Value("${kakao.client.id}")
    private String clientId;

    @Value("${kakao.redirect.uri}")
    private String redirectUri;

    private static final String KAKAO_TOKEN_URL = "https://kauth.kakao.com/oauth/token";
    private static final String KAKAO_USER_INFO_URL = "https://kapi.kakao.com/v2/user/me";

    private UserRepository userRepository;
    private final WebClient webClient;

    public KakaoAuthService(UserRepository userRepository, WebClient.Builder webClientBuilder ){
        this.userRepository = userRepository;
        this.webClient = webClientBuilder.build();
    }

    public Mono<String> getKakaoAuthUrl() {
        String url = "https://kauth.kakao.com/oauth/authorize" +
                "?client_id=" + clientId +
                "&redirect_uri=" + redirectUri +
                "&response_type=code";
        return Mono.just(url);
    }


    public Mono<TokenResponse> getAccessTokens(String code) {
        return webClient.post()
                .uri(KAKAO_TOKEN_URL)
                .body(BodyInserters.fromFormData("grant_type","authorization_code")
                        .with("client_id",clientId)
                        .with("redirect_uri",redirectUri)
                        .with("code",code)
                ).retrieve()
                .bodyToMono(String.class)
                .flatMap(responseBody ->{
                    try{
                        ObjectMapper objectMapper = new ObjectMapper();
                        JsonNode jsonNode = objectMapper.readTree(responseBody);
                        String accessToken = jsonNode.get("access_token").asText();
                        String refreshToken = jsonNode.get("refresh_token").asText();
                        return Mono.just(new TokenResponse(accessToken, refreshToken, null));
                    } catch (Exception e){
                        return Mono.error(new RuntimeException("엑세스토큰 파싱 실패"));
                    }
                });
    }

    public Mono<KakaoUserResponse> getUserInfo(String accessToken) {
        return webClient
                .get()
                .uri(KAKAO_USER_INFO_URL)
                .headers(headers -> headers.setBearerAuth(accessToken))
                .retrieve()
                .bodyToMono(KakaoUserResponse.class);
    }


    public Mono<TokenResponse> handleUserLogin(String code){
        return getAccessTokens(code)
                .flatMap(tokens -> getUserInfo(tokens.getAccessToken())
                        .flatMap(userInfo ->{
                            String email = userInfo.getKakaoAccount().getEmail();
                            String name = userInfo.getKakaoAccount().getProfile().getNickname();

                            return Mono.defer(()->{
                                Optional<User> optionalUser = userRepository.findByEmail(email);
                                String redirectURl;
                                if(optionalUser.isPresent()){
                                    redirectURl= "http://localhost:5173";
                                }else {
                                    User newUser = User.builder()
                                            .email(email)
                                            .name(name)
                                            .build();
                                    userRepository.save(newUser);
                                    redirectURl="http://localhost:5173/selectRole";
                                }
                                return Mono.just(new TokenResponse(tokens.getAccessToken(), tokens.getRefreshToken(),redirectURl));
                            });

                        }));
    }

    public Mono<TokenResponse> refreshAccessToken(String refreshToken){
        return webClient.post()
                .uri(KAKAO_TOKEN_URL)
                .body(BodyInserters.fromFormData("grant_type","refresh_token")
                        .with("client_id",clientId)
                        .with("refresh_token",refreshToken))
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(responseBody->{
                    try{
                        ObjectMapper objectMapper =new ObjectMapper();
                        JsonNode jsonNode = objectMapper.readTree(responseBody);
                        String newAccessToken = jsonNode.get("access_token").asText();
                        String newRefreshToken = jsonNode.has("refresh_token")?jsonNode.get("refresh_token").asText():refreshToken;
                        return Mono.just(new TokenResponse(newAccessToken, newRefreshToken,null));
                    }catch (Exception e){
                        return Mono.error(new RuntimeException("Access Token 갱신 실패"));
                    }
                });
    }
    public Mono<Void> logout(String accessToken){
        return webClient.post()
                .uri("https://kapi.kakao.com/v1/user/logout")
                .headers(headers->headers.setBearerAuth(accessToken))
                .retrieve()
                .bodyToMono(Void.class);
    }
//    public Mono<User> handleUserLogin(String code) {
//        return getAccessToken(code)
//                .flatMap(this::getUserInfo)
//                .flatMap(userInfo -> {
//                    String email = userInfo.getKakaoAccount().getEmail();
//                    String name = userInfo.getKakaoAccount().getProfile().getNickname();
//
//                    return Mono.defer(() -> {
//                        Optional<User> optionalUser = userRepository.findByEmail(email);
//                        if (optionalUser.isPresent()) {
//                            return Mono.just(optionalUser.get());
//                        } else {
//                            User newUser = User.builder()
//                                    .email(email)
//                                    .name(name)
//                                    .build();
//                            return Mono.just(userRepository.save(newUser));
//                        }
//
//                    });
//                });
//    };
}
