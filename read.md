<details>
<summary> 5️⃣ AI를 활용한 금융 뉴스 요약</summary>
<div markdown="1">

### 에그머니에서는?

최신 금융 뉴스를 효율적으로 수집하고 요약하여 에그머니 사용자, 특히 고등학생 자녀에게 제공하는 것을 목표로 합니다. 자동화된 크롤링 및 요약 프로세스를 통해 정보 접근성을 높이며, 복잡한 뉴스를 쉽게 이해할 수 있도록 변환하는 데 기여합니다. 자녀는 언제든지 최신 금융 뉴스에 대한 정보를 간편하게 얻을 수 있어, 금융에 대한 이해도를 높이고 실제 투자 및 금융 활동에 대한 자신감을 키울 수 있습니다.

### 1. 뉴스 크롤링
- **사용 기술**: [Jsoup](https://jsoup.org/)
- **설명**: Jsoup 라이브러리를 사용하여 네이버 금융 뉴스 섹션(예: `https://news.naver.com/section/101`)에서 최신 뉴스를 크롤링합니다. HTML 문서를 파싱하고, DOM을 탐색하여 뉴스 제목, 링크, 출처를 추출합니다. 이 과정에서 오류가 발생할 수 있는 예외 처리를 포함하여 안정성을 높였습니다.

- **핵심 메서드**: 
    ```java
    @Override
    public List<NewsCrawlResponse> crawlFinanceHeadLineNews() {
        Document document = Jsoup.connect(FINANCE_URL)
                .userAgent(USER_AGENT)
                .get();
        
        Elements headLineNewsElements = document
                .select("div.section_article.as_headline._TEMPLATE ul.sa_list > li");

        // 제목, 링크, 출처 추출
        for (Element headLineElement : headLineNewsElements) {
            String title = headLineElement.selectFirst("div.sa_text > a").text();
            String link = headLineElement.selectFirst("div.sa_text > a").attr("href");
            String press = headLineElement.selectFirst("div.sa_text_press").text();

            newsInfos.add(new NewsCrawlResponse(title, link, press));
        }

        return newsInfos;
    }
    ```
- **예외 처리**: 크롤링 과정에서 `IOException`이 발생할 수 있으며, 이를 로깅하여 문제 발생 시 디버깅에 도움을 줍니다. 헤드라인 뉴스가 없는 경우에도 경고 로그를 기록합니다.

### 2. 뉴스 내용 요약
- **사용 기술**: [OpenAI GPT](https://openai.com/)
- **설명**: 크롤링한 뉴스의 내용을 OpenAI API를 통해 요약합니다. 사용자가 이해하기 쉬운 형태로 요약하기 위해 "너는 경제 뉴스를 고등학생이 이해할 수 있도록 요약해주는 전문 기자야"라는 프롬프트를 설정합니다. 이 과정을 통해 사용자는 복잡한 금융 뉴스를 간결하게 파악할 수 있습니다.

- **핵심 메서드**:
    ```java
    @Override
    public SummarizedContentResponse summarizeNews(String newsContent) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-4o-mini");
        requestBody.put("max_tokens", 700);
        requestBody.put("temperature", 0.3);
        requestBody.put("messages", new Object[] {
                Map.of("role", "system", "content", "너는 경제 뉴스를 고등학생이 이해할 수 있도록 요약해주는 전문 기자야"),
                Map.of("role", "user", "content", newsContent)
        });

        return this.webClient.post()
                .uri("https://api.openai.com/v1/chat/completions")
                .header("Authorization", "Bearer " + openAIConfig.getApiKey())
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(OpenAIResponse.class)
                .map(response -> {
                    String content = response.getChoices().get(0).getMessage().getContent();
                    return new SummarizedContentResponse(response.getId(), content);
                })
                .block();
    }
    ```
- **요약 결과 처리**: 요약된 결과는 사용자가 쉽게 이해할 수 있도록 변환된 후, 저장하거나 사용자에게 전달하는 등의 추가 처리로 이어질 수 있습니다.

### 3. 스케줄링
- **사용 기술**: [Spring Scheduler](https://docs.spring.io/spring-framework/docs/current/reference/html/integration.html#scheduling)
- **설명**: Spring의 스케줄링 기능을 활용하여 매일 특정 시간(예: 4시 55분)에 뉴스 크롤링 및 요약 작업을 자동으로 수행합니다. 이를 통해 반복적인 작업을 자동화하고, 사용자가 항상 최신 뉴스를 받을 수 있도록 합니다. CRON 표현식을 사용하여 정확한 시간에 작업을 수행할 수 있습니다.

- **핵심 메서드**:
    ```java
    @Scheduled(cron = "0 55 4 * * *", zone = "Asia/Seoul")
    public void scheduledNewsCrawlingAndSummarization() {
        List<NewsCrawlResponse> newsList = crawlFinanceHeadLineNews();
        for (NewsCrawlResponse news : newsList) {
            String content = crawlNewsContent(news.getLink());
            SummarizedContentResponse summary = summarizeNews(content);
            // 저장 또는 추가 처리
        }
    }
    ```
- **유연성**: 스케줄링 설정은 환경에 따라 조정 가능하며, 필요에 따라 다양한 시간 주기로 설정할 수 있습니다.


</div>
</details>
