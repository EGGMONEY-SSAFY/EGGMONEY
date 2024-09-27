package com.ssafy.eggmoney.stock.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.ssafy.eggmoney.common.config.StockApiConfig;
import com.ssafy.eggmoney.stock.dto.response.StockPriceResponse;
import com.ssafy.eggmoney.stock.dto.response.StockPricesResponse;
import com.ssafy.eggmoney.stock.dto.response.StockTokenResponse;
import com.ssafy.eggmoney.stock.entity.Stock;
import com.ssafy.eggmoney.stock.entity.StockItem;
import com.ssafy.eggmoney.stock.repository.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class StockServiceImpl implements StockService {
    private final StockApiConfig apiConfig;
    private final WebClient webClient;
    private final StockRepository stockRepository;

    public StockServiceImpl(StockApiConfig stockApiConfig, WebClient.Builder webClientBuilder, StockRepository stockRepository) {
        this.apiConfig = stockApiConfig;
        this.webClient = webClientBuilder.baseUrl("https://openapi.koreainvestment.com:9443").build();
        this.stockRepository = stockRepository;
    }

    @Override
    public StockTokenResponse getAccessToken() {
        return webClient.post()
                .uri("/oauth2/tokenP")
                .bodyValue(Map.of(
                        "grant_type", "client_credentials",
                        "appkey", apiConfig.getAppKey(),
                        "appsecret", apiConfig.getAppSecret()
                ))
                .retrieve()
                .bodyToMono(StockTokenResponse.class)
                .block();
    }

    @Override
    public List<StockPriceResponse> getStockPrices(String token, String inputDate, String stockCode) {
        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/uapi/domestic-stock/v1/quotations/inquire-index-daily-price")
                        .queryParam("FID_PERIOD_DIV_CODE", "D")
                        .queryParam("FID_COND_MRKT_DIV_CODE", "U")
                        .queryParam("FID_INPUT_ISCD", stockCode)
                        .queryParam("FID_INPUT_DATE_1", inputDate)
                        .build())
                .headers(headers -> {
                    headers.set("content-type", "application/json");
                    headers.set("authorization", "Bearer " + token);
                    headers.set("appkey", apiConfig.getAppKey());
                    headers.set("appsecret", apiConfig.getAppSecret());
                    headers.set("tr_id", "FHPUP02120000");
                    headers.set("custtype", "P");
                })
                .retrieve()
                .bodyToMono(StockPricesResponse.class)
                .map(StockPricesResponse::getOutput2)
                .block();
    }

    public BigDecimal getCurrentStockPrice(String token, String stockCode) {
        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/uapi/domestic-stock/v1/quotations/inquire-index-price")
                        .queryParam("FID_COND_MRKT_DIV_CODE", "U")
                        .queryParam("FID_INPUT_ISCD", stockCode)
                        .build())
                .headers(headers -> {
                    headers.set("content-type", "application/json");
                    headers.set("authorization", "Bearer " + token);
                    headers.set("appkey", apiConfig.getAppKey());
                    headers.set("appsecret", apiConfig.getAppSecret());
                    headers.set("tr_id", "FHPUP02100000");
                    headers.set("custtype", "P");
                })
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(jsonNode -> new BigDecimal(jsonNode.get("output").get("bstp_nmix_prpr").asText()))
                .block();
    }

    @Transactional
    @Override
    public void saveStockPrices(List<StockPriceResponse> stockPrices, StockItem stockItem) {
        List<Stock> stocks = new ArrayList<>();
        stockPrices.forEach(stockPrice -> {
            Stock stock = new Stock(stockItem, stockPrice.getBstp_nmix_prpr(), stockPrice.getStck_bsop_date());
            stocks.add(stock);
        });
        stockRepository.saveAll(stocks);
    }

    @Transactional
    public void saveCurrentStockPrices(List<Stock> stocks) {
        stockRepository.saveAll(stocks);
    }
}
