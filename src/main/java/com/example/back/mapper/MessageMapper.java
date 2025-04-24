package com.example.back.mapper;

import com.example.back.pojo.Message;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MessageMapper {
    List<Message> get_messages_by_conversation_id(@Param("conversation_id") Integer conversation_id, 
                                                @Param("offset") Integer offset, 
                                                @Param("page_size") Integer page_size);

    void create_message(Message message);

    void mark_messages_as_read(@Param("conversation_id") Integer conversation_id, 
                             @Param("reader_id") Integer reader_id);

    Integer get_unread_count(@Param("conversation_id") Integer conversation_id, 
                           @Param("user_id") Integer user_id);

    Message get_latest_message(@Param("conversation_id") Integer conversation_id);
} 