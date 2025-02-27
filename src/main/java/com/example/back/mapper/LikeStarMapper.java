package com.example.back.mapper;

import com.example.back.pojo.LikeStar;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LikeStarMapper {

    // 插入操作
    int insertOperation(@Param("user_id") Integer user_id,
                        @Param("target_id") Integer target_id,
                        @Param("content_type") String content_type,
                        @Param("operation_type") String operation_type);

    // 删除操作
    int deleteOperation(@Param("user_id") Integer user_id,
                        @Param("target_id") Integer target_id,
                        @Param("content_type") String content_type,
                        @Param("operation_type") String operation_type);

    // 查询操作
    List<LikeStar> searchList(@Param("user_id") Integer user_id,
                          @Param("content_type") String content_type,
                          @Param("operation_type") String operation_type);

    List<LikeStar> searchCount(@Param("user_id") Integer user_id,
                               @Param("comment_id") Integer comment_id,
                               @Param("article_id") Integer article_id,
                               @Param("operation_type") String operation_type);

    // 新增根据 target_id 查询数据条数的方法
    int countByTargetId(@Param("target_id") Integer target_id);
}
