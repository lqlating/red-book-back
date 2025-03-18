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

    // 删除订阅
    @Delete("DELETE FROM subscript WHERE user_id = #{userId} AND target_id = #{targetId}")
    int deleteSubscript(@Param("userId") int userId, @Param("targetId") int targetId);

    @Select("SELECT user_id FROM subscript WHERE target_id = #{targetId}")
    List<Integer> findUserIdsByTargetId(@Param("targetId") Integer targetId);

    // 查询 target_id 等于 userId 的数据条数
    @Select("SELECT COUNT(*) FROM subscript WHERE target_id = #{userId}")
    int countSubscriptionsByTargetId(@Param("userId") Integer userId);

    // 新增查询 user_id 等于传入 userId 的数据条数
    @Select("SELECT COUNT(*) FROM subscript WHERE user_id = #{userId}")
    int countSubscriptionsByUserId(@Param("userId") Integer userId);
}
