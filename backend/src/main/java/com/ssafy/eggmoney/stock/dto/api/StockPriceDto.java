package com.ssafy.eggmoney.stock.dto.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
public class StockPriceDto {
    private LocalDate stck_bsop_date;
    private BigDecimal bstp_nmix_prpr;

    @JsonCreator
    public StockPriceDto(@JsonProperty("stck_bsop_date") String stck_bsop_date,
                         @JsonProperty("bstp_nmix_prpr") BigDecimal bstp_nmix_prpr) {
        this.stck_bsop_date = LocalDate.parse(stck_bsop_date, DateTimeFormatter.ofPattern("yyyyMMdd"));
        this.bstp_nmix_prpr = bstp_nmix_prpr;
    }
}
