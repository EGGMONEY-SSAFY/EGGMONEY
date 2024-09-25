package com.ssafy.eggmoney.news.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.eggmoney.news.dto.response.NewsTitlesResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

import static com.ssafy.eggmoney.news.entity.QNews.news;

public class NewsCostomRepositoryImpl implements NewsCostomRepository {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public NewsCostomRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<NewsTitlesResponse> findNewsTitles() {
        List<NewsTitlesResponse> newsTitles = queryFactory
                .select(Projections.constructor(NewsTitlesResponse.class,
                        news.id, news.title, news.press, news.createdAt))
                .from(news)
                .orderBy(news.createdAt.desc())
                .fetch();

        if(newsTitles.isEmpty()) {
            throw new EntityNotFoundException("뉴스 데이터가 존재하지않습니다.");
        }

        return newsTitles;
    }
}
