package com.example.back.mapper;

import com.example.back.pojo.Conversation;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ConversationMapper {
    /**
     * 获取用户的所有会话
     * @param userId 用户ID
     * @return 会话列表
     */
    List<Conversation> get_conversations_by_user_id(@Param("userId") Long userId);

    /**
     * 根据两个用户ID获取会话
     * @param userLowId 较小的用户ID
     * @param userHighId 较大的用户ID
     * @return 会话信息
     */
    Conversation get_conversation_by_user_ids(@Param("userLowId") Long userLowId, 
                                         @Param("userHighId") Long userHighId);

    /**
     * 根据会话ID获取会话
     * @param conversationId 会话ID
     * @return 会话信息
     */
    Conversation get_conversation_by_id(@Param("conversationId") Long conversationId);

    /**
     * 创建新会话
     * @param conversation 会话信息
     */
    void create_conversation(Conversation conversation);

    /**
     * 更新会话的最后消息
     * @param conversation 会话信息
     */
    void update_last_message(Conversation conversation);

    /**
     * 增加低ID用户的未读消息数
     * @param conversationId 会话ID
     */
    void increment_low_unread(@Param("conversationId") Long conversationId);

    /**
     * 增加高ID用户的未读消息数
     * @param conversationId 会话ID
     */
    void increment_high_unread(@Param("conversationId") Long conversationId);

    /**
     * 清空低ID用户的未读消息数
     * @param conversationId 会话ID
     */
    void clear_low_unread(@Param("conversationId") Long conversationId);

    /**
     * 清空高ID用户的未读消息数
     * @param conversationId 会话ID
     */
    void clear_high_unread(@Param("conversationId") Long conversationId);
} 