package com.ssafy.eggmoney.news.repository;

import com.ssafy.eggmoney.news.dto.response.NewsTitlesResponse;

import java.util.List;

public interface NewsCostomRepository {
    List<NewsTitlesResponse> findNewsTitles();
}
