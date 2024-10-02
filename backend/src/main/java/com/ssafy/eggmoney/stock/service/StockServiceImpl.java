package com.ssafy.eggmoney.stock.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.ssafy.eggmoney.common.config.StockApiConfig;
import com.ssafy.eggmoney.stock.dto.api.StockPriceDto;
import com.ssafy.eggmoney.stock.dto.api.StockPricesDto;
import com.ssafy.eggmoney.stock.dto.api.StockTokenDto;
import com.ssafy.eggmoney.stock.dto.response.StockPriceForYearResponse;
import com.ssafy.eggmoney.stock.dto.response.StockPriceResponse;
import com.ssafy.eggmoney.stock.entity.Stock;
import com.ssafy.eggmoney.stock.entity.StockItem;
import com.ssafy.eggmoney.stock.repository.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.*;

@Service
@Transactional(readOnly = true)
public class StockServiceImpl implements StockService {
    private final StockApiConfig apiConfig;
    private final WebClient webClient;
    private final StockRepository stockRepository;

    private StockItem[] stockItems = {StockItem.KOSPI, StockItem.KOSDAQ, StockItem.AUTOMOTIVE,
            StockItem.SEMICONDUCTOR, StockItem.HEALTHCARE, StockItem.BANKING, StockItem.ENERGY_CHEMICAL,
            StockItem.STEEL, StockItem.CONSTRUCTION, StockItem.TRANSPORTATION, StockItem.MEDIA_ENTERTAINMENT,
            StockItem.IT, StockItem.UTILITIES};

    @Override
    public StockItem[] getStockItems() {
        return stockItems;
    }

    public StockServiceImpl(StockApiConfig stockApiConfig, WebClient.Builder webClientBuilder, StockRepository stockRepository) {
        this.apiConfig = stockApiConfig;
        this.webClient = webClientBuilder.baseUrl("https://openapi.koreainvestment.com:9443").build();
        this.stockRepository = stockRepository;
    }

    @Override
    public StockTokenDto getAccessToken() {
        return webClient.post()
                .uri("/oauth2/tokenP")
                .bodyValue(Map.of(
                        "grant_type", "client_credentials",
                        "appkey", apiConfig.getAppKey(),
                        "appsecret", apiConfig.getAppSecret()
                ))
                .retrieve()
                .bodyToMono(StockTokenDto.class)
                .block();
    }

    public List<StockPriceDto> getStockPrices(String token, String inputDate, String stockCode) {
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
                .bodyToMono(StockPricesDto.class)
                .map(StockPricesDto::getOutput2)
                .block();
    }

    @Transactional
    public void saveStockPrices(List<StockPriceDto> stockPrices, StockItem stockItem) {
        List<Stock> stocks = new ArrayList<>();
        stockPrices.forEach(stockPrice -> {
//            Stock stock = new Stock(stockItem, stockPrice.getBstp_nmix_prpr(), stockPrice.getStck_bsop_date());
//            stocks.add(stock);
        });
        stockRepository.saveAll(stocks);
    }

    @Override
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
    public void saveCurrentStockPrices(List<Stock> stocks) {
        stockRepository.saveAll(stocks);
    }

    @Override
    public List<StockPriceResponse> findLatestStockPrices() {
        List<StockPriceResponse> StockPricesRes = new ArrayList<>();
        StockItem[] stockItems = {StockItem.KOSPI, StockItem.KOSDAQ, StockItem.AUTOMOTIVE,
                StockItem.SEMICONDUCTOR, StockItem.HEALTHCARE, StockItem.BANKING, StockItem.ENERGY_CHEMICAL,
                StockItem.STEEL, StockItem.CONSTRUCTION, StockItem.TRANSPORTATION, StockItem.MEDIA_ENTERTAINMENT,
                StockItem.IT, StockItem.UTILITIES};

        for (StockItem stockItem : stockItems) {
            List<Stock> stocks = stockRepository.findTop2ByStockItemOrderByCreatedAtDesc(stockItem);

            if(stocks.size() < 2) {
                throw new NoSuchElementException("최신 주식 가격이 조회 되지 않습니다.");
            }

            Stock stock = stocks.get(0);
            StockPricesRes.add(new StockPriceResponse(stock.getId(), stockItem, stock.getUpdatedAt(),
                    stock.getStockPrice(), stocks.get(1).getStockPrice()));
        }

        return StockPricesRes;
    }

    @Override
    public LocalDateTime findLatestDate() {
        return stockRepository.findLatestDate();
    }

    @Override
    public List<StockPriceForYearResponse> findStockPricesForYear(StockItem stockItem) {
        return stockRepository.findStockPricesForYear(stockItem);
    }

    @Override
    public Stock findByStockItemAndDate(StockItem stockItem) {
        LocalDateTime latestDate = findLatestDate();
        return stockRepository.findByStockItemAndCreatedAt(stockItem, latestDate)
                .orElseThrow(() -> new NoSuchElementException("해당 지수가 조회 되지 않습니다."));
    }

}
