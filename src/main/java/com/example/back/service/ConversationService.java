package com.example.back.service;

import com.example.back.pojo.Conversation;
import com.example.back.pojo.Message;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ConversationService {
    /**
     * 获取用户的所有会话列表
     * @param user_id 用户ID
     * @return 会话列表
     */
    List<Conversation> get_conversations(Long user_id);

    /**
     * 创建新会话
     * @param user_id 当前用户ID
     * @param target_user_id 目标用户ID
     * @return 创建的会话信息
     */
    Conversation create_conversation(Long user_id, Long target_user_id);

    /**
     * 获取会话中的消息列表
     * @param conversation_id 会话ID
     * @param page 页码
     * @param page_size 每页数量
     * @return 消息列表
     */
    List<Message> get_messages(Long conversation_id, Integer page, Integer page_size);

    /**
     * 发送文本消息
     * @param conversation_id 会话ID
     * @param sender_id 发送者ID
     * @param content 消息内容
     * @return 发送的消息
     */
    Message send_text_message(Long conversation_id, Long sender_id, String content);

    /**
     * 发送图片消息
     * @param conversation_id 会话ID
     * @param sender_id 发送者ID
     * @param image 图片文件
     * @return 发送的消息
     */
    Message send_image_message(Long conversation_id, Long sender_id, MultipartFile image);

    /**
     * 标记会话中的消息为已读
     * @param conversation_id 会话ID
     * @param reader_id 阅读者ID
     */
    void mark_messages_as_read(Long conversation_id, Long reader_id);

    /**
     * 获取会话中的未读消息数量
     * @param conversation_id 会话ID
     * @param user_id 用户ID
     * @return 未读消息数量
     */
    Integer get_unread_count(Long conversation_id, Long user_id);

    /**
     * 获取用户所有会话的未读消息总数
     * @param user_id 用户ID
     * @return 未读消息总数
     */
    Integer get_total_unread_count(Long user_id);
} 