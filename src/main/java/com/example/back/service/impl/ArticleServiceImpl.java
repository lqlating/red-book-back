package com.example.back.service.impl;
import com.example.back.mapper.ArticleMapper;
import com.example.back.pojo.Article;
import com.example.back.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public List<Article> list(String type) {
        return articleMapper.list(type);
    }

    @Override
    public void addLike(Integer articleId) {
        articleMapper.addLike(articleId);
    }

    @Override
    public void subLike(Integer articleID) {
        articleMapper.subLike(articleID);
    }

    @Override
    public void addStar(Integer articleID) {
        articleMapper.addStar(articleID);
    }

    @Override
    public void subStar(Integer articleID) {
        articleMapper.subStar(articleID);
    }

}
