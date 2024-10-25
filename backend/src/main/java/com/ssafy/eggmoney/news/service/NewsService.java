package com.ssafy.eggmoney.news.service;

import com.ssafy.eggmoney.news.dto.response.NewsCrawlResponse;
import com.ssafy.eggmoney.news.dto.response.NewsReponse;
import com.ssafy.eggmoney.news.dto.response.NewsTitlesResponse;
import com.ssafy.eggmoney.news.dto.response.SummarizedContentResponse;
import com.ssafy.eggmoney.news.entity.News;

import java.util.List;

public interface NewsService {
    List<NewsCrawlResponse> crawlFinanceHeadLineNews();
    String crawlNewsContent(String newsUrl);
    SummarizedContentResponse summarizeNews(String newsContent);
    void saveAllNews(List<News> newsList);
    List<NewsTitlesResponse> findNewsTitles(long page);
    NewsReponse findNewsById(Long id);
}
