package com.ssafy.eggmoney.common.webClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class ApiClient {
    private final WebClient.Builder webClientBuilder;
    private final ObjectMapper objectMapper;

    // 공통 메서드: WebClient로 POST 요청을 보내고 응답을 처리
    public Mono<String> sendPostRequest(String url, String requestBody) {
        WebClient webClient = webClientBuilder.baseUrl("https://finopenapi.ssafy.io").build();
        return webClient.post()
                .uri(url)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(error -> {
                    if (error instanceof WebClientResponseException) {
                        WebClientResponseException ex = (WebClientResponseException) error;
                        String errorResponseBody = ex.getResponseBodyAsString();
                        try {
                            HashMap<String, Object> errorResponseMap = objectMapper.readValue(errorResponseBody, new TypeReference<HashMap<String, Object>>() {});
                            handleErrorResponse(errorResponseMap);
                        } catch (JsonProcessingException e) {

                        }
                    }
                });
    }

    // 응답을 JSON으로 변환하는 처리
    public <T> T parseResponse(String responseBody, TypeReference<T> typeReference) {
        try {
            return objectMapper.readValue(responseBody, typeReference);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON 파싱 에러 발생", e);
        }
    }

    // UserKey 조회 로직
    public Mono<String> findUserKey(String email) {
        String userKeyUrl = "/ssafy/api/v1/member/search";
        String requestBody = String.format("""
        {
            "apiKey": "063446596d794b47bb3d4977043e3523",
            "userId": "%s"
        }
        """, email);

        return sendPostRequest(userKeyUrl, requestBody);
    }

    // 계좌 잔고 조회 로직
    public Mono<String> getAccountBalance(String userKey, String transactionUniqueNo) {
        String requestBody = String.format("""
        {
            "Header": {
                "apiName": "inquireDemandDepositAccountList",
                "transmissionDate": "%s",
                "transmissionTime": "%s",
                "institutionCode": "00100",
                "fintechAppNo": "001",
                "apiServiceCode": "inquireDemandDepositAccountList",
                "institutionTransactionUniqueNo": "%s",
                "apiKey": "063446596d794b47bb3d4977043e3523",
                "userKey": "%s"
            }
        }
        """, generateTransmissionDate(), generateTransmissionTime(), transactionUniqueNo, userKey);

        String balanceUrl = "/ssafy/api/v1/edu/demandDeposit/inquireDemandDepositAccountList";
        return sendPostRequest(balanceUrl, requestBody);
    }

    // 계좌 이체 로직
    public Mono<String> transferAccount(String userKey, String childAccount, String parentAccount, String transactionUniqueNo, int withdrawalPrice) {
        String requestBody = String.format("""
        {
            "Header": {
                "apiName": "updateDemandDepositAccountTransfer",
                "transmissionDate": "%s",
                "transmissionTime": "%s",
                "institutionCode": "00100",
                "fintechAppNo": "001",
                "apiServiceCode": "updateDemandDepositAccountTransfer",
                "institutionTransactionUniqueNo": "%s",
                "apiKey": "063446596d794b47bb3d4977043e3523",
                "userKey": "%s"
            },
            "depositAccountNo": "%s",
            "depositTransactionSummary": "아이 : 에그머니 입금",
            "transactionBalance": "%s",
            "withdrawalAccountNo": "%s",
            "withdrawalTransactionSummary": "부모 : 에그머니 출금"
        }
        """, generateTransmissionDate(), generateTransmissionTime(), transactionUniqueNo, userKey, childAccount, withdrawalPrice, parentAccount);

        String transferUrl = "/ssafy/api/v1/edu/demandDeposit/updateDemandDepositAccountTransfer";
        return sendPostRequest(transferUrl, requestBody);
    }

//    예외처리 로직
    public void handleErrorResponse(HashMap<String, Object> errorResponseMap) {
        String responseCode = String.valueOf(errorResponseMap.get("responseCode"));
        switch (responseCode) {
            case "H1007":
                System.out.println("기관거래고유번호 중복");
                break;
            case "H1008":
                System.out.println("API-KEY 유효하지 않음");
                break;
            case "H1009":
                System.out.println("USER-KEY 유효하지 않음");
                break;
            case "A1003":
                System.out.println("계좌번호가 유효하지 않음");
                break;
            case "A1011":
                System.out.println("거래금액이 유효하지 않음");
                break;
            case "A1016":
                System.out.println("1회 이체 가능 한도 초과");
                break;
            case "A1017":
                System.out.println("1일 이체 가능 한도 초과");
                break;
            default:
                System.out.println("알 수 없는 오류: " + responseCode);
                break;
        }
    }

    // 현재 날짜를 yyyyMMdd 형식으로 반환
    public String generateTransmissionDate() {
        return java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    // 현재 시간을 HHmmss 형식으로 반환
    public String generateTransmissionTime() {
        return java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("HHmmss"));
    }

    // 고유번호 생성기
    public String generateTransactionUniqueNo(BigInteger institutionTransactionUniqueNo) {
        String transmissionDate = generateTransmissionDate();  // yyyyMMdd
        String transmissionTime = generateTransmissionTime();  // HHmmss

        // 6자리 고유번호와 함께 결합하여 고유 거래 번호 생성
        return transmissionDate + transmissionTime + String.format("%06d", institutionTransactionUniqueNo);
    }

}
