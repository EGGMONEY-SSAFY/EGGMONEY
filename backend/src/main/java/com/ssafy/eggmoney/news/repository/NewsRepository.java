package com.ssafy.eggmoney.news.repository;

import com.ssafy.eggmoney.news.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long>, NewsCostomRepository {
}
