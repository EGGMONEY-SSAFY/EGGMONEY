package com.ssafy.eggmoney.news.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SummarizedContentResponse {
    private String id;
    private String content;
    private String refusal;
    private String finishReason;
    private int promptTokens;
    private int completionTokens;
}
