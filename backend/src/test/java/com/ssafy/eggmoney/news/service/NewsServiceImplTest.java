package com.ssafy.eggmoney.news.service;

import com.ssafy.eggmoney.news.dto.response.NewsCrawlResponse;
import com.ssafy.eggmoney.news.dto.response.SummarizedContentResponse;
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

//    @Test
//    void crawlFinanceNews() {
//        List<NewsCrawlResponse> headLineNews = newsService.crawlFinanceHeadLineNews();
//        for (NewsCrawlResponse news : headLineNews) {
//            String newsContent = newsService.crawlNewsContent(news.getUrl());
//            System.out.println(news.getUrl());
//            System.out.println(newsContent);
//            SummarizedContentResponse summarizedContent = newsService.summarizeNews(newsContent);
//            System.out.println(summarizedContent.getContent());
//            System.out.println(summarizedContent.getRefusal());
//            System.out.println(summarizedContent.getFinishReason());
//            System.out.println(summarizedContent.getPromptTokens());
//            System.out.println(summarizedContent.getCompletionTokens());
//        }
//
//        for (int i = 0; i < 2; i++) {
//            String newsContent = newsService.crawlNewsContent(headLineNews.get(i).getUrl());
//            System.out.println(headLineNews.get(i).getUrl());
//            System.out.println(newsContent);
//            newsContent = "줄바꿈 X, 350토큰이상, 존댓말. 뉴스: " + newsContent;
//            SummarizedContentResponse summarizedContent = newsService.summarizeNews(newsContent);
//            System.out.println(summarizedContent.getContent());
//            System.out.println(summarizedContent.getRefusal());
//            System.out.println(summarizedContent.getFinishReason());
//            System.out.println(summarizedContent.getPromptTokens());
//            System.out.println(summarizedContent.getCompletionTokens());
//        }
//    }
}