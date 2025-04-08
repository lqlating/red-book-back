package com.example.back.mapper;

import com.example.back.pojo.Message;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MessageMapper {
    List<Message> get_messages_by_conversation_id(@Param("conversation_id") Integer conversation_id, 
                                                @Param("offset") Integer offset, 
                                                @Param("page_size") Integer page_size);

    @Insert("INSERT INTO messages (conversation_id, sender_id, content, image_url, created_at, is_read) " +
            "VALUES (#{conversation_id}, #{sender_id}, #{content}, #{image_url}, #{created_at}, #{is_read})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void create_message(Message message);

    @Update("UPDATE messages SET is_read = true " +
            "WHERE conversation_id = #{conversation_id} AND sender_id != #{reader_id}")
    void mark_messages_as_read(@Param("conversation_id") Integer conversation_id, 
                             @Param("reader_id") Integer reader_id);

    @Select("SELECT COUNT(*) FROM messages " +
            "WHERE conversation_id = #{conversation_id} " +
            "AND sender_id != #{user_id} " +
            "AND is_read = false")
    Integer get_unread_count(@Param("conversation_id") Integer conversation_id, 
                           @Param("user_id") Integer user_id);
} 