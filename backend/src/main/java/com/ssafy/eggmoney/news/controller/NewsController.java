package com.ssafy.eggmoney.news.controller;

import com.ssafy.eggmoney.news.dto.response.NewsReponse;
import com.ssafy.eggmoney.news.dto.response.NewsTitlesResponse;
import com.ssafy.eggmoney.news.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class NewsController {
    private final NewsService newsService;

    @GetMapping("/news")
    public ResponseEntity<List<NewsTitlesResponse>> getNewsTitles(@RequestParam(defaultValue = "1") long page) {
        return new ResponseEntity<>(newsService.findNewsTitles(page), HttpStatus.OK);
    }

    @GetMapping("news/{newsId}")
    public ResponseEntity<NewsReponse> getNewsById(@PathVariable Long newsId) {
        return new ResponseEntity<>(newsService.findNewsById(newsId), HttpStatus.OK);
    }
}
