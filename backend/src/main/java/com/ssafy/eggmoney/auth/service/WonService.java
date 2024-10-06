package com.ssafy.eggmoney.auth.service;


import com.ssafy.eggmoney.user.dto.reqeust.UpdateUserRequestDto;
import com.ssafy.eggmoney.user.entity.User;
import com.ssafy.eggmoney.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class WonService {

    private final WebClient webClient;
    private final UserService userServcie;
    public WonService(WebClient.Builder webClientBuilder, UserService userServcie){
        this.webClient = webClientBuilder.build();
        this.userServcie = userServcie;
    }

    public Mono<String> sendmessage(String accountnum, User user) {
        String url = "https://finopenapi.ssafy.io/ssafy/api/v1/edu/accountAuth/openAccountAuth";

        String requestBody = String.format("{\"Header\": {\"apiName\": \"openAccountAuth\", \"transmissionDate\": \"%s\", \"transmissionTime\": \"%s\", \"institutionCode\": \"00100\", \"fintechAppNo\": \"001\", \"apiServiceCode\": \"openAccountAuth\", \"institutionTransactionUniqueNo\": \"%s\", \"apiKey\": \"063446596d794b47bb3d4977043e3523\", \"userKey\": \"2c07499f-9e20-4800-a1ae-a45a4382d4d8\"}, \"accountNo\": \"%s\", \"authText\": \"SSAFY_TEST\"}",
                getCurrentDate(), getCurrentTime(), generateUniqueTransactionNo(), accountnum);

        return webClient.post()
                .uri(url)
                .header("Content-Type", "application/json") // Content-Type 헤더 추가
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(error -> {
                    if (error instanceof WebClientResponseException) {
                        WebClientResponseException webClientResponseException = (WebClientResponseException) error;
                        System.err.println("Error response: " + webClientResponseException.getResponseBodyAsString());
                    } else {
                        System.err.println("1원 검증 전송 실패: " + error.getMessage());
                    }
                });
    }

    public Mono<String> checkmessage(String accountnum, String authText, String authnum, User user) {
        String url = "https://finopenapi.ssafy.io/ssafy/api/v1/edu/accountAuth/checkAuthCode";
        String requestBody = String.format(
                "{\"Header\": {\"apiName\": \"checkAuthCode\", \"transmissionDate\": \"%s\", \"transmissionTime\": \"%s\", \"institutionCode\": \"00100\", \"fintechAppNo\": \"001\", \"apiServiceCode\": \"checkAuthCode\", \"institutionTransactionUniqueNo\": \"%s\", \"apiKey\": \"063446596d794b47bb3d4977043e3523\", \"userKey\": \"2c07499f-9e20-4800-a1ae-a45a4382d4d8\"}, \"accountNo\": \"%s\", \"authText\": \"%s\", \"authCode\": \"%s\"}",
                getCurrentDate(), getCurrentTime(),generateUniqueTransactionNo(), accountnum, authText, authnum
        );
//        요청을 확인하고, 유저 데이터를 해당 아래 컨트롤러를 활용해서 업데이트하는 로직 피룡
//        @PostMapping("/{userId}/update")
//    public void updateUser(@PathVariable("userId") Long userId, @RequestBody UpdateUserRequestDto dto){
//        userService.updateUser(userId, dto);
//    }
//        userServcie.createUser(Long userId, String authnum);
        return webClient.post()
                .uri(url)
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .doOnSuccess(response -> {
                    // 인증 성공 후 유저 업데이트 로직 호출
                    UpdateUserRequestDto updateDto = new UpdateUserRequestDto();
                    updateDto.setRealAccount(accountnum);
                    //updateDto.setAuthStatus(true); // 인증 성공 상태로 업데이트
                    userServcie.updateUser(user, updateDto); // userId로 사용자 정보 업데이트
                })
                .doOnError(error -> {
                    if (error instanceof WebClientResponseException) {
                        WebClientResponseException webClientResponseException = (WebClientResponseException) error;
                        System.err.println("Error response: " + webClientResponseException.getResponseBodyAsString());
                    } else {
                        System.err.println("Failed to check message: " + error.getMessage());
                    }
                });

    }

    private String getCurrentDate(){
        return new SimpleDateFormat("yyyyMMdd").format(new Date());
    }

    private String getCurrentTime(){
        return new SimpleDateFormat("HHmmss").format(new Date());
    }
    private String generateUniqueTransactionNo() {
        // 고유한 거래 번호 생성 로직
        String transNo = "2024021"+String.valueOf(System.currentTimeMillis());
        return transNo;
    }
}
