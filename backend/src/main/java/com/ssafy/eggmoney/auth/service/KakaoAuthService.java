package com.ssafy.eggmoney.auth.service;

import com.ssafy.eggmoney.auth.dto.response.KakaoUserResponse;
import com.ssafy.eggmoney.user.entity.User;
import com.ssafy.eggmoney.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

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


    public Mono<String> getAccessToken(String code) {
        return webClient.post()
                .uri(KAKAO_TOKEN_URL)
                .body(BodyInserters.fromFormData("grant_type","authorization_code")
                        .with("client_id",clientId)
                        .with("redirect_uri",redirectUri)
                        .with("code",code)
                ).retrieve()
                .bodyToMono(String.class);
    }

    public Mono<KakaoUserResponse> getUserInfo(String accessToken) {
        return webClient.get()
                .uri(KAKAO_USER_INFO_URL)
                .headers(headers -> headers.setBearerAuth(accessToken))
                .retrieve()
                .bodyToMono(KakaoUserResponse.class);
    }

    public Mono<User> handleUserLogin(String code) {
        return getAccessToken(code)
                .flatMap(this::getUserInfo)
                .flatMap(userInfo -> {
                    String email = userInfo.getKakaoAccount().getEmail();
                    String name = userInfo.getKakaoAccount().getProfile().getNickname();

                    return Mono.defer(() -> {
                        Optional<User> optionalUser = userRepository.findByEmail(email);
                        if (optionalUser.isPresent()) {
                            return Mono.just(optionalUser.get());
                        } else {
                            User newUser = User.builder()
                                    .email(email)
                                    .name(name)
                                    .build();
                            return Mono.just(userRepository.save(newUser));
                        }

                    });
                });
    };
}
