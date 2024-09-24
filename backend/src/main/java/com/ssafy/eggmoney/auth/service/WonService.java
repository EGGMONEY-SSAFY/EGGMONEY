package com.ssafy.eggmoney.auth.service;


import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class WonService {

    private final WebClient webClient;

    public WonService(WebClient.Builder webClientBuilder){
        this.webClient = webClientBuilder.build();
    }

    public Mono<String> sendmessage(String accountnum) {
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

    public Mono<String> checkmessage(String accountnum, String authText, String authnum) {
        String url = "https://finopenapi.ssafy.io/ssafy/api/v1/edu/accountAuth/checkAuthCode";
        String requestBody = String.format(
                "{\"Header\": {\"apiName\": \"checkAuthCode\", \"transmissionDate\": \"%s\", \"transmissionTime\": \"%s\", \"institutionCode\": \"00100\", \"fintechAppNo\": \"001\", \"apiServiceCode\": \"checkAuthCode\", \"institutionTransactionUniqueNo\": \"%s\", \"apiKey\": \"063446596d794b47bb3d4977043e3523\", \"userKey\": \"2c07499f-9e20-4800-a1ae-a45a4382d4d8\"}, \"accountNo\": \"%s\", \"authText\": \"%s\", \"authCode\": \"%s\"}",
                getCurrentDate(), getCurrentTime(),generateUniqueTransactionNo(), accountnum, authText, authnum
        );

        return webClient.post()
                .uri(url)
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
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
