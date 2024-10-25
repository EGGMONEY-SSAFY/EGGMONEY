package com.ssafy.eggmoney.news.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class OpenAIResponse {
    private String id; // API 호출을 추적하는 데 사용
    private List<Choice> choices;
    private Usage usage;

    @Getter
    @AllArgsConstructor
    public static class Choice {
        private Message message;
        private String finishReason;
    }

    @Getter
    @AllArgsConstructor
    public static class Message {
        private String content;
        private String refusal;
    }

    @Getter
    @AllArgsConstructor
    public static class Usage {
        @JsonProperty("prompt_tokens")
        private int promptTokens;
        @JsonProperty("completion_tokens")
        private int completionTokens;
    }
}
