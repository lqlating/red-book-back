package com.example.back.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface CountMapper {
    @Select("SELECT COUNT(*) FROM comment WHERE article_id = #{article_id} AND is_banned = 0;")
    int getCommentCount(Integer article_id);
}
