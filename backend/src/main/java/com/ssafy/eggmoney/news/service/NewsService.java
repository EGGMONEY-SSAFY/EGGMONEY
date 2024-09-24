package com.ssafy.eggmoney.news.service;

import com.ssafy.eggmoney.news.dto.response.NewsCrawlResponse;
import com.ssafy.eggmoney.news.dto.response.SummarizedContentResponse;
import com.ssafy.eggmoney.news.entity.News;

import java.util.List;

public interface NewsService {
    List<NewsCrawlResponse> crawlFinanceHeadLineNews();
    String crawlNewsContent(String newsUrl);
    SummarizedContentResponse summarizeNews(String newsContent);
    void saveAllNews(List<News> newsList);
}
