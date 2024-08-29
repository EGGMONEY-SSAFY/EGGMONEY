package com.example.alrambot.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class NotificationService {

    private final RestTemplate restTemplate;

    public NotificationService(){
        this.restTemplate = new RestTemplate();
    }

    public void sendNotification(String message){
        String url = "웹훅 개인url";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> request = new HashMap<>();
        request.put("text", message);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(request, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

        if (response.getStatusCode().is2xxSuccessful()){
            System.out.println("알람 메시지 전송 완료: " + message);
        } else {
            System.err.println("알람 전송 실패"+message);
        }
    }

}
