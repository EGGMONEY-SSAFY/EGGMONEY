package com.ssafy.eggmoney.news.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NewsCrawlResponse {
    private String title;
    private String link;
    private String press;
}
