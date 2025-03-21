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

    // 新增方法：获取所有 is_review 为 0 的文章数据
    List<Article> getUnreviewedArticles();

    // 新增方法：获取所有 is_banned 为 1 的文章数据
    List<Article> getBannedArticles();

    // 新增方法：将指定文章的 is_review 和 is_banned 设置为 1
    void setReviewedAndBanned(Integer articleId);

    Object getArticleById(Integer id);
}