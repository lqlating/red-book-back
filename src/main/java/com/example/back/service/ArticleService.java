package com.example.back.service;

import com.example.back.pojo.Article;
import com.example.back.pojo.ArticleRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ArticleService {
    List<Article> list(String type);

    // 新增：获取指定类型且作者ID不等于指定ID的文章
    List<Article> listExcludeAuthor(String type, Integer id);

    void addLike(Integer articleId);

    void subLike(Integer articleID);

    void addStar(Integer articleID);

    void subStar(Integer articleID);

    List<Article> getArticlesByIds(List<Integer> articleIds);

    List<Article> searchByTitleOrContent(String keyword);

    // 新增：根据关键词搜索文章且排除指定作者
    List<Article> searchByTitleOrContentExcludeAuthor(String keyword, Integer id);

    List<Article> getArticlesByAuthorId(Integer authorId);

    void save(ArticleRequest article);

    // 新增方法：获取所有 is_review 为 0 的文章数据
    List<Article> getUnreviewedArticles();

    // 新增方法：获取所有 is_banned 为 1 的文章数据
    List<Article> getBannedArticles();

    // 新增方法：将指定文章的 is_review 和 is_banned 设置为 1
    void setReviewedAndBanned(Integer articleId);

    Object getArticleById(Integer id);

    // 新增方法：将文章标记为已审核
    void setReviewed(Integer articleId);

    // 新增方法：将指定文章的 is_banned 设置为 0（解封文章）
    void unbanArticle(Integer articleId);

    // 新增方法：删除文章
    void deleteArticle(Integer article_id);

    // 根据关键词搜索文章标题或内容，可选排除指定作者的文章
    List<Article> searchByTitleOrContentWithFilter(String keyword, Integer excludeAuthorId);
}