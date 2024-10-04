package com.ssafy.eggmoney.news.scheduler;

import com.ssafy.eggmoney.news.dto.response.NewsCrawlResponse;
import com.ssafy.eggmoney.news.dto.response.SummarizedContentResponse;
import com.ssafy.eggmoney.news.entity.News;
import com.ssafy.eggmoney.news.service.NewsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class NewsScheduler {
    private final NewsService newsService;

//    @Scheduled(cron = "0 51 16 * * *", zone = "Asia/Seoul")
    public void saveSummarizeAndCrawlNews(){
        log.info("뉴스 스케쥴링 시작: " + LocalDateTime.now());
        List<News> newsList = new ArrayList<>();
        List<NewsCrawlResponse> headLineNewsList = newsService.crawlFinanceHeadLineNews();

        for(NewsCrawlResponse headLineNews : headLineNewsList){
            News news = new News(headLineNews.getTitle(), headLineNews.getLink(), headLineNews.getPress());
            String newsContent = newsService.crawlNewsContent(headLineNews.getLink());
            newsContent = "문단 단위로 줄 바꿈, 300자이상, 존댓말. 뉴스: " + newsContent;
            SummarizedContentResponse summarizedContent = newsService.summarizeNews(newsContent);

            if(summarizedContent.getFinishReason() == null && summarizedContent.getRefusal() == null) {
                news.addContent(summarizedContent.getContent());
                newsList.add(news);
            } else if (summarizedContent.getFinishReason() != null && summarizedContent.getRefusal() == null) {
                log.info("OpenAI API 응답 중단: " + summarizedContent.getFinishReason());
                log.info("OpenAI 생성응답 토큰: " + summarizedContent.getCompletionTokens());
            } else {
                log.warn("OpenAI API 요청 거절: " + summarizedContent.getRefusal());
            }
        }

        newsService.saveAllNews(newsList);
    }
}
