package com.ssafy.eggmoney.news.service;

import com.ssafy.eggmoney.common.config.OpenAIApiConfig;
import com.ssafy.eggmoney.news.dto.response.NewsCrawlResponse;
import com.ssafy.eggmoney.news.dto.response.OpenAIResponse;
import com.ssafy.eggmoney.news.dto.response.SummarizedContentResponse;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.*;

@Service
@Slf4j
public class NewsServiceImpl implements NewsService {
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/128.0.6613.138 Safari/537.36";
    private final WebClient webClient;
    private final OpenAIApiConfig openAIConfig;

    public NewsServiceImpl(WebClient.Builder webClientBuilder, OpenAIApiConfig openAIConfig) {
        this.webClient = webClientBuilder.baseUrl("https://api.openai.com/v1").build();
        this.openAIConfig = openAIConfig;
    }

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

    public SummarizedContentResponse summarizeNews(String newsContent) {
        // 요청 본문 설정
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-4o-mini");
        requestBody.put("max_tokens", 700);
        requestBody.put("temperature", 0.3);
        requestBody.put("messages", new Object[] {
                Map.of("role", "system", "content", "너는 경제 뉴스를 고등학생이 이해할 수 있도록 요약해주는 전문기자야"),
                Map.of("role", "user", "content", newsContent)
        });

        // OpenAI API로 요청
        return this.webClient.post()
                .uri("https://api.openai.com/v1/chat/completions")
                .header("Authorization", "Bearer " + openAIConfig.getApiKey())
                .bodyValue(requestBody) // 요청 본문 설정
                .retrieve()
                .bodyToMono(OpenAIResponse.class)
                .map(response -> {
                    OpenAIResponse.Choice firstChoice = response.getChoices().get(0);
                    String id = response.getId();
                    String content = firstChoice.getMessage().getContent();
                    String refusal = firstChoice.getMessage().getRefusal();
                    String finishReason = firstChoice.getFinishReason();
                    int promptTokens = response.getUsage().getPromptTokens();
                    int completionTokens = response.getUsage().getCompletionTokens();
                    return new SummarizedContentResponse(id, content, refusal, finishReason, promptTokens, completionTokens);
                })
                .block();
    }
}
