package com.practices.controller;

import com.practices.dto.ArticleRequest;
import com.practices.dto.ArticleResponse;
import com.practices.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/article")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createArticle(@RequestBody ArticleRequest articleRequest) {

        articleService.createArticle(articleRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ArticleResponse> getAllArticles() {
        return articleService.getAllArticles();
    }
}
