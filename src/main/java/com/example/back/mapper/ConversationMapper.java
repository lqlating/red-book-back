package com.example.back.mapper;

import com.example.back.pojo.Conversation;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ConversationMapper {
    @Select("SELECT * FROM conversations WHERE user_low_id = #{user_id} OR user_high_id = #{user_id} ORDER BY last_message_time DESC")
    List<Conversation> get_conversations_by_user_id(@Param("user_id") Integer user_id);

    @Select("SELECT * FROM conversations WHERE user_low_id = #{user_low_id} AND user_high_id = #{user_high_id}")
    Conversation get_conversation_by_user_ids(@Param("user_low_id") Integer user_low_id, @Param("user_high_id") Integer user_high_id);

    @Insert("INSERT INTO conversations (user_low_id, user_high_id, last_message, last_message_time, low_unread, high_unread) " +
            "VALUES (#{user_low_id}, #{user_high_id}, #{last_message}, #{last_message_time}, #{low_unread}, #{high_unread})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void create_conversation(Conversation conversation);

    @Update("UPDATE conversations SET last_message = #{last_message}, last_message_time = #{last_message_time} WHERE id = #{id}")
    void update_last_message(Conversation conversation);

    @Update("UPDATE conversations SET low_unread = low_unread + 1 WHERE id = #{id}")
    void increment_low_unread(@Param("id") Integer id);

    @Update("UPDATE conversations SET high_unread = high_unread + 1 WHERE id = #{id}")
    void increment_high_unread(@Param("id") Integer id);

    @Update("UPDATE conversations SET low_unread = 0 WHERE id = #{id}")
    void clear_low_unread(@Param("id") Integer id);

    @Update("UPDATE conversations SET high_unread = 0 WHERE id = #{id}")
    void clear_high_unread(@Param("id") Integer id);
} 