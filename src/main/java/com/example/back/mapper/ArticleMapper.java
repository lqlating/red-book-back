package com.example.back.mapper;

import com.example.back.pojo.Article;
import com.example.back.pojo.ArticleRequest;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ArticleMapper {
    @Select("SELECT * FROM article WHERE txt_type = #{type} AND is_review = 1 AND is_banned = 0;")
    List<Article> list(String type);

    @Update("UPDATE article\n" +
            "SET like_count = like_count + 1\n" +
            "WHERE article_id = #{articleId};\n")
    void addLike(Integer articleId);

    @Update("UPDATE article\n" +
            "SET like_count = like_count - 1\n" +
            "WHERE article_id = #{articleID};\n")
    void subLike(Integer articleID);

    @Update("UPDATE article\n" +
            "SET star_count = star_count + 1\n" +
            "WHERE article_id = #{articleID};\n")
    void addStar(Integer articleID);

    @Update("UPDATE article\n" +
            "SET star_count = star_count - 1\n" +
            "WHERE article_id = #{articleID};\n")
    void subStar(Integer articleID);

    //根据id查询文章
    @Select({
            "<script>",
            "SELECT * FROM article WHERE article_id IN",
            "<foreach item='articleId' collection='articleIds' open='(' separator=',' close=')'>",
            "#{articleId}",
            "</foreach>",
            "</script>"
    })
    List<Article> selectArticlesByIds(@Param("articleIds") List<Integer> articleIds);

    //根据输入内容查询文章
    @Select("SELECT * FROM article WHERE title LIKE CONCAT('%', #{keyword}, '%') OR content LIKE CONCAT('%', #{keyword}, '%')")
    List<Article> searchByTitleOrContent(@Param("keyword") String keyword);

    // 新增方法：根据 author_id 查询文章
    @Select("SELECT * FROM article WHERE author_id = #{authorId}")
    List<Article> findArticlesByAuthorId(Integer authorId);

    // 插入文章的 SQL 语句
    @Insert("INSERT INTO article (img, img_url, txt_type, content, title, like_count, publication_time, address, star_count, author_id, is_review, is_banned) " +
            "VALUES (#{imgData}, #{imgUrl}, #{txtType}, #{content}, #{title}, #{likeCount}, #{publicationTime}, #{address}, #{starCount}, #{authorId}, #{isReview}, #{isBanned})")
    void insert(ArticleRequest article);

    // 新增方法：获取所有 is_review 为 0 的文章数据
    @Select("SELECT * FROM article WHERE is_review = 0;")
    List<Article> getUnreviewedArticles();

    // 新增方法：获取所有 is_banned 为 1 的文章数据
    @Select("SELECT * FROM article WHERE is_banned = 1;")
    List<Article> getBannedArticles();

    // 新增方法：将指定文章的 is_review 和 is_banned 设置为 1
    @Update("UPDATE article SET is_review = 1, is_banned = 1 WHERE article_id = #{articleId};")
    void setReviewedAndBanned(@Param("articleId") Integer articleId);

    // 新增方法：将指定文章的 is_review 设置为 1
    @Update("UPDATE article SET is_review = 1 WHERE article_id = #{articleId}")
    void setReviewed(@Param("articleId") Integer articleId);

    // 新增方法：将指定文章的 is_banned 设置为 0（解封文章）
    @Update("UPDATE article SET is_banned = 0 WHERE article_id = #{articleId}")
    void unbanArticle(@Param("articleId") Integer articleId);

    Object getArticleById(Integer id);
}