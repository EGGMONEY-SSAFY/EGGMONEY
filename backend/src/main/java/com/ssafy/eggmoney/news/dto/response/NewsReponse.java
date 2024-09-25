package com.ssafy.eggmoney.news.dto.response;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class NewsReponse {
    private Long id;
    private String title;
    private String link;
    private String press;
    private String content;
    private LocalDate publishDate;

    public NewsReponse(Long id, String title, String link, String press,
                       String content, LocalDateTime publishDate) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.press = press;
        this.content = content;
        this.publishDate = publishDate.toLocalDate();
    }
}
