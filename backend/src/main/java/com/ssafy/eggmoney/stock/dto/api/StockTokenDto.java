package com.ssafy.eggmoney.stock.dto.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;


import java.time.LocalDateTime;

@Getter
public class StockTokenDto {
    private String accessToken;
    private LocalDateTime accessTokenTokenExpired;
    private String tokenType;
    private int expiresIn;

    @JsonCreator
    public StockTokenDto(@JsonProperty("access_token") String accessToken,
                         @JsonProperty("acess_token_token_expired") LocalDateTime accessTokenTokenExpired,
                         @JsonProperty("token_type") String tokenType,
                         @JsonProperty("expires_in") int expiresIn) {
        this.accessToken = accessToken;
        this.accessTokenTokenExpired = accessTokenTokenExpired;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
    }
}
