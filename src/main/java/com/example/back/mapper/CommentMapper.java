package com.example.back.mapper;

import com.example.back.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentMapper {
    @Select("SELECT * FROM comment\n" +
            "WHERE article_id = #{article_id}\n" +
            "ORDER BY like_count DESC;\n")
    List<Comment> getComment(Integer article_id);
}
