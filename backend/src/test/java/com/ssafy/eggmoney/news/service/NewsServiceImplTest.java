package com.ssafy.eggmoney.news.service;

import com.ssafy.eggmoney.news.dto.response.NewsCrawlResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class NewsServiceImplTest {
    @Autowired
    private NewsServiceImpl newsService;

//    @Test
//    void crawlFinanceHeadLineNews() {
//        List<NewsCrawlResponse> headLineNews = newsService.crawlFinanceHeadLineNews();
//        Assertions.assertThat(headLineNews).hasSizeGreaterThan(0);
//    }
//
//    @Test
//    void crawlFinanceNews() {
//        List<NewsCrawlResponse> headLineNews = newsService.crawlFinanceHeadLineNews();
//        for (NewsCrawlResponse news : headLineNews) {
//            String s = newsService.crawlNewsContent(news.getUrl());
//        }
//    }
}