package com.practices.service;

import com.practices.dto.ArticleRequest;
import com.practices.dto.ArticleResponse;
import com.practices.model.Article;
import com.practices.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleService {

    private final ArticleRepository articleRepository;


    public void createArticle(ArticleRequest articleRequest) {
        Article article = Article.builder()
                .name(articleRequest.getName())
                .description(articleRequest.getDescription())
                .price(articleRequest.getPrice())
                .build();

        articleRepository.save(article);
        log.info("Article with id: {} is saved.", article.getId());
    }

    public List<ArticleResponse> getAllArticles() {

        List<Article> articles = articleRepository.findAll();
        return articles.stream().map(this::mapToArticleResponse).collect(Collectors.toList());
    }

    private ArticleResponse mapToArticleResponse(Article article) {
        return ArticleResponse.builder()
                .id(article.getId())
                .name(article.getName())
                .description(article.getDescription())
                .price(article.getPrice())
                .build();
    }
}
