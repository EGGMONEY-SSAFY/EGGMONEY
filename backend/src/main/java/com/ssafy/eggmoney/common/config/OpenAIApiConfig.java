package com.ssafy.eggmoney.common.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class OpenAIApiConfig {
    @Value("${open_ai.key}")
    private String apiKey;
}
