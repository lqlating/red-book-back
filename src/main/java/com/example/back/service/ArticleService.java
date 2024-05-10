package com.example.back.service;

import com.example.back.pojo.Article;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ArticleService {
    List<Article> list(String type);

    void addLike(Integer articleId);

    void subLike(Integer articleID);


    void addStar(Integer articleID);

    void subStar(Integer articleID);
}
