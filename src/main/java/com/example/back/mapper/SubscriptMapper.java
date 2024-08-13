package com.example.back.mapper;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SubscriptMapper {
    @Insert("INSERT INTO subscript (user_id, target_id) VALUES (#{userId}, #{targetId})")
    void insertSubscription(@Param("userId") Integer userId, @Param("targetId") Integer targetId);

    // 根据 user_id 查询所有的 target_id
    @Select("SELECT target_id FROM subscript WHERE user_id = #{userId}")
    List<Integer> findTargetIdsByUserId(@Param("userId") Integer userId);

    // 新增删除订阅的方法
    @Delete("DELETE FROM subscript WHERE user_id = #{userId} AND target_id = #{targetId}")
    int deleteSubscript(@Param("userId") int userId, @Param("targetId") int targetId);
}
