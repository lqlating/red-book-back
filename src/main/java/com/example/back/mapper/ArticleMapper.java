package com.example.back.mapper;

import com.example.back.pojo.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ArticleMapper {
//    List<Article> list(String type);
    @Select("SELECT * FROM article WHERE txt_type = #{type};\n")
    List<Article> list(String type);

    @Update("UPDATE article\n" +
            "SET like_count = like_count + 1\n" +
            "WHERE article_id = #{article_id};\n")
    void addLike(Integer articleId);

    @Update("UPDATE article\n" +
            "SET like_count = like_count - 1\n" +
            "WHERE article_id = #{article_id};\n")
    void subLike(Integer articleID);


    @Update("UPDATE article\n" +
            "SET star_count = star_count + 1\n" +
            "WHERE article_id = #{article_id};\n")
    void addStar(Integer articleID);


    @Update("UPDATE article\n" +
            "SET star_count = star_count - 1\n" +
            "WHERE article_id = #{article_id};\n")
    void subStar(Integer articleID);
}