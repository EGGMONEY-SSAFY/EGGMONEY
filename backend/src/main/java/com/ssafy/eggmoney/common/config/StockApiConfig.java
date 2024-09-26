package com.ssafy.eggmoney.common.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class StockApiConfig {
    @Value("${stock_api.appkey}")
    private String appKey;

    @Value("${stock_api.appsecret}")
    private String appSecret;
}