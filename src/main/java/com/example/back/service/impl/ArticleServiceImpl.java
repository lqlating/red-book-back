package com.example.back.service.impl;

import com.example.back.mapper.ArticleMapper;
import com.example.back.pojo.Article;
import com.example.back.pojo.ArticleRequest;
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

    @Override
    public List<Article> getArticlesByIds(List<Integer> articleIds) {
        return articleMapper.selectArticlesByIds(articleIds);
    }

    @Override
    public List<Article> searchByTitleOrContent(String keyword) {
        return articleMapper.searchByTitleOrContent(keyword);
    }

    @Override
    public List<Article> getArticlesByAuthorId(Integer authorId) {
        return articleMapper.findArticlesByAuthorId(authorId);
    }

    @Override
    public List<Article> getUnreviewedArticles() {
        return articleMapper.getUnreviewedArticles();
    }

    @Override
    public void save(ArticleRequest article) {
        articleMapper.insert(article);
    }

    // 新增方法：获取所有 is_banned 为 1 的文章数据
    @Override
    public List<Article> getBannedArticles() {
        return articleMapper.getBannedArticles();
    }

    // 新增方法：将指定文章的 is_review 和 is_banned 设置为 1
    @Override
    public void setReviewedAndBanned(Integer articleId) {
        articleMapper.setReviewedAndBanned(articleId);
    }

    @Override
    public Object getArticleById(Integer id) {
        return articleMapper.getArticleById(id);
    }
}