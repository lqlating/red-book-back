package com.example.back.mapper;

import com.example.back.pojo.Article;
import com.example.back.pojo.ArticleRequest;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ArticleMapper {
    List<Article> list(String type);

    List<Article> listWithFilter(@Param("type") String type, @Param("id") Integer id);

    void addLike(Integer articleId);

    void subLike(Integer articleID);

    void addStar(Integer articleID);

    void subStar(Integer articleID);

    List<Article> selectArticlesByIds(@Param("articleIds") List<Integer> articleIds);

    List<Article> searchByTitleOrContent(@Param("keyword") String keyword);

    List<Article> searchByTitleOrContentWithFilter(@Param("keyword") String keyword, @Param("id") Integer id);

    List<Article> searchByTitleOrContentExcludeAuthor(@Param("keyword") String keyword, @Param("id") Integer id);

    List<Article> findArticlesByAuthorId(Integer authorId);

    void insert(ArticleRequest article);

    List<Article> getUnreviewedArticles();

    List<Article> getBannedArticles();

    void setReviewedAndBanned(@Param("articleId") Integer articleId);

    void setReviewed(@Param("articleId") Integer articleId);

    void unbanArticle(@Param("articleId") Integer articleId);

    void deleteArticle(@Param("article_id") Integer article_id);

    Article getArticleById(Integer id);

    List<Article> listExcludeAuthor(@Param("type") String type, @Param("id") Integer id);
    
    List<Integer> findArticleStates(int id);
}