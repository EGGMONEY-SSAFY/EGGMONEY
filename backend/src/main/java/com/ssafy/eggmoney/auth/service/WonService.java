package com.ssafy.eggmoney.auth.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
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

    public Mono<String> sendmessage(String accountnum){
        String url = "https://finopenapi.ssafy.io/ssafy/api/v1/edu/accountAuth/openAccountAuth";

        return webClient.post()
                .uri(url)
                .header("apiName", "openAccountAuth")
                .header("transmissionDate", getCurrentDate())
                .header("transmissionTime", getCurrentTime())
                .header("institutionCode", "00100")
                .header("apiServiceCode", "openAccountAuth")
                .header("institutionTransactionUniqueNo", "20240215121212123557")
                .header("apiKey", "063446596d794b47bb3d4977043e3523")
                .header("userKey", "2c07499f-9e20-4800-a1ae-a45a4382d4d8")
                .bodyValue(String.format("{\"accountNo\": \"%s\", \"authText\": \"SSAFY_TEST\"}", accountnum))
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(error -> System.err.println("1원 검증 전송 실패: " + error.getMessage()));
    }

    public Mono<String> checkmessage(String accountnum, String authText, String authnum) {
        String url = "https://finopenapi.ssafy.io/ssafy/api/v1/edu/accountAuth/checkAuthCode";

        return webClient.post()
                .uri(url)
                .header("apiName", "checkAuthCode")
                .header("transmissionDate", getCurrentDate())
                .header("transmissionTime", getCurrentTime())
                .header("institutionCode", "00100")
                .header("fintechAppNo", "001")
                .header("apiServiceCode", "checkAuthCode")
                .header("institutionTransactionUniqueNo", "20240215121212123494")
                .header("apiKey", "063446596d794b47bb3d4977043e3523")
                .header("userKey", "2c07499f-9e20-4800-a1ae-a45a4382d4d8")
                .bodyValue(String.format("{\"accountNo\": \"%s\", \"authText\": \"%s\", \"authCode\": \"%s\"}", accountnum, authText, authnum))
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(error -> System.err.println("Failed to check message: " + error.getMessage()));
    }

    private String getCurrentDate(){
        return new SimpleDateFormat("yyyyMMdd").format(new Date());
    }

    private String getCurrentTime(){
        return new SimpleDateFormat("HHmmss").format(new Date());
    }
}
