package com.ssafy.eggmoney.news.dto.response;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class NewsTitlesResponse {
    private Long id;
    private String title;
    private String press;
    private LocalDate publishDate;

    public NewsTitlesResponse(Long id, String title, String press, LocalDateTime publishDate) {
        this.id = id;
        this.title = title;
        this.press = press;
        this.publishDate = publishDate.toLocalDate();
    }
}
