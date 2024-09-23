package com.ssafy.eggmoney.news.service;

import com.ssafy.eggmoney.news.dto.response.NewsCrawlResponse;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
public class NewsServiceImpl implements NewsService {
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/128.0.6613.138 Safari/537.36";

    public List<NewsCrawlResponse> crawlFinanceHeadLineNews() {
        List<NewsCrawlResponse> newsInfos = new ArrayList<NewsCrawlResponse>();
        final String FINANCE_URL = "https://news.naver.com/section/101";

        Document document = null;
        try {
            document = Jsoup.connect(FINANCE_URL)
                    .userAgent(USER_AGENT)
                    .get();
        } catch (IOException e) {
            log.error("크롤링 에러 발생: ", e);
        }

        Elements headLineNewsElements = document.select("div.section_article.as_headline._TEMPLATE ul.sa_list > li");

        for (Element headLineElement : headLineNewsElements) {
            Element titleElement = headLineElement.selectFirst("div.sa_text > a");
            String title = titleElement.text();
            String link = titleElement.attr("href");

            Element pressElement = headLineElement.selectFirst("div.sa_text_press");
            String press = pressElement.text();

            NewsCrawlResponse newsInfo = new NewsCrawlResponse(title, link, press);
            newsInfos.add(newsInfo);
        }

        return newsInfos;
    }

    public String crawlNewsContent(String newsUrl) {
        List<NewsCrawlResponse> newsInfos = new ArrayList<NewsCrawlResponse>();

        Document document = null;
        try {
            document = Jsoup.connect(newsUrl)
                    .userAgent(USER_AGENT)
                    .get();
        } catch (IOException e) {
            log.error("크롤링 에러 발생: ", e);
        }

        Element newsElement = document.selectFirst("article#dic_area");
        if (newsElement != null) {
            newsElement.select("strong, span.end_photo_org").remove();
            return newsElement.text();
        } else {
            throw new NoSuchElementException("뉴스 내용이 없습니다.");
        }
    }
}
