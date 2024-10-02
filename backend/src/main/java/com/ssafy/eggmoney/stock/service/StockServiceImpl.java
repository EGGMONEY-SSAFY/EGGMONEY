package com.ssafy.eggmoney.stock.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.ssafy.eggmoney.common.config.StockApiConfig;
import com.ssafy.eggmoney.stock.dto.api.StockPriceDto;
import com.ssafy.eggmoney.stock.dto.api.StockPricesDto;
import com.ssafy.eggmoney.stock.dto.api.StockTokenDto;
import com.ssafy.eggmoney.stock.dto.response.StockPriceForYearResponse;
import com.ssafy.eggmoney.stock.dto.response.StockPriceResponse;
import com.ssafy.eggmoney.stock.entity.Stock;
import com.ssafy.eggmoney.stock.entity.StockPrice;
import com.ssafy.eggmoney.stock.entity.StockItem;
import com.ssafy.eggmoney.stock.repository.StockPriceRepository;
import com.ssafy.eggmoney.stock.repository.StockRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class StockServiceImpl implements StockService {
    private final StockApiConfig apiConfig;
    private final WebClient webClient;
    private final StockRepository stockRepository;
    private final StockPriceRepository stockPriceRepository;

    private StockItem[] stockItems = {StockItem.KOSPI, StockItem.KOSDAQ, StockItem.AUTOMOTIVE,
            StockItem.SEMICONDUCTOR, StockItem.HEALTHCARE, StockItem.BANKING, StockItem.ENERGY_CHEMICAL,
            StockItem.STEEL, StockItem.CONSTRUCTION, StockItem.TRANSPORTATION, StockItem.MEDIA_ENTERTAINMENT,
            StockItem.IT, StockItem.UTILITIES};

    @Override
    public StockItem[] getStockItems() {
        return stockItems;
    }

    public StockServiceImpl(StockApiConfig stockApiConfig, WebClient.Builder webClientBuilder,
                            StockRepository stockRepository, StockPriceRepository stockPriceRepository) {
        this.apiConfig = stockApiConfig;
        this.webClient = webClientBuilder.baseUrl("https://openapi.koreainvestment.com:9443").build();
        this.stockRepository = stockRepository;
        this.stockPriceRepository = stockPriceRepository;
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

    @Override
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
    @Override
    public void saveStockPrices(Stock stock, List<StockPriceDto> stockPriceDtos) {
//        List<StockPrice> stockPrices = new ArrayList<>();
//        stockPriceDtos.forEach(stockPriceDto -> {
//            StockPrice stockprice = new StockPrice(stock, stockPriceDto.getBstp_nmix_prpr(), stockPriceDto.getStck_bsop_date());
//            stockPrices.add(stockprice);
//        });
//        stockPriceRepository.saveAll(stockPrices);
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
    @Override
    public void saveCurrentStockPrices(List<StockPrice> stockPrices) {
        stockPriceRepository.saveAll(stockPrices);
    }

    @Override
    public List<StockPriceResponse> findLatestStockPrices() {
        PageRequest pageReq = PageRequest.of(1, 13, Sort.by(Sort.Direction.DESC, "updatedAt"));
        List<StockPrice> stockPrices = stockPriceRepository.findJoinStock(pageReq);

        if(stockPrices.isEmpty()) {
            throw new NoSuchElementException("지수 가격들을 찾을 수 없습니다.");
        }

        List<StockPriceResponse> stockPriceResponses = stockPrices.stream().map(stockPrice -> new StockPriceResponse(
                stockPrice.getStock().getId(), stockPrice.getStock().getStockItem(),
                stockPrice.getStock().getUpdatedAt(), stockPrice.getStock().getCurrentPrice(), stockPrice.getPrice()
        )).collect(Collectors.toList());

        stockPriceResponses.sort(Comparator.comparing(StockPriceResponse::getStockId));

        return stockPriceResponses;
    }

    @Override
    public List<StockPriceForYearResponse> findStockPricesForYear(StockItem stockItem) {
        return stockRepository.findStockPricesForYear(stockItem);
    }
}
