package com.ssafy.eggmoney.news.entity;

import com.ssafy.eggmoney.common.entity.BaseTime;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.*;

@Getter
@Entity
@Table(name = "news")
@NoArgsConstructor(access = PROTECTED)
@Slf4j
public class News extends BaseTime {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "news_id")
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String link;

    @NotNull
    private String press;

    @Column(length = 2000)
    private String content;

    public News(String title, String link, String press) {
        this.title = title;
        this.link = link;
        this.press = press;
    }

    public void addContent(String content) {
        if (this.content == null) {
            this.content = content;
        } else {
            log.error("News 엔티티 값 추가 에러: 이미 content 값이 존재합니다.");
            throw new IllegalStateException("News 엔티티 값 추가 에러: 이미 content 값이 존재합니다.");
        }
    }
}
