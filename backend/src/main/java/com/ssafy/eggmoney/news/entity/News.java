package com.ssafy.eggmoney.news.entity;

import com.ssafy.eggmoney.common.entity.BaseTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.*;

@Getter
@Entity
@Table(name = "news")
@NoArgsConstructor(access = PROTECTED)
public class News extends BaseTime {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "news_id")
    private Long id;

    private String newsTitle;
    private String newsContent;
    private String newsLink;

    // 발행일시 : created_at
}
