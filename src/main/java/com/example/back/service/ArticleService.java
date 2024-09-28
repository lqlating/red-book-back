package com.example.back.service;

import com.example.back.pojo.Article;
import com.example.back.pojo.ArticleRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ArticleService {
    List<Article> list(String type);

    void addLike(Integer articleId);

    void subLike(Integer articleID);


    void addStar(Integer articleID);

    void subStar(Integer articleID);

    List<Article> getArticlesByIds(List<Integer> articleIds);


    List<Article> searchByTitleOrContent(String keyword);

    List<Article> getArticlesByAuthorId(Integer authorId);

    void save(ArticleRequest article);
}
