package com.ssafy.eggmoney.stock.service;

import com.ssafy.eggmoney.stock.dto.response.StockTokenResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StockServiceImplTest {
    @Autowired StockService stockService;

    @Test
    void getToken() {
        StockTokenResponse tokenResponse = stockService.getAccessToken();

        Assertions.assertThat(tokenResponse.getAccessToken()).isNotNull();
        Assertions.assertThat(tokenResponse.getExpiresIn()).isEqualTo(86400);
    }
}