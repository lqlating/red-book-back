package com.example.back.service;

import com.example.back.pojo.Message;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MessageService {
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
     * 发送Base64格式的图片消息
     * @param conversation_id 会话ID
     * @param sender_id 发送者ID
     * @param base64Image Base64格式的图片数据
     * @return 发送的消息
     */
    Message send_base64_image_message(Long conversation_id, Long sender_id, String base64Image);
    
    /**
     * 标记消息为已读
     * @param conversation_id 会话ID
     * @param reader_id 阅读者ID
     */
    void mark_messages_as_read(Long conversation_id, Long reader_id);
    
    /**
     * 获取未读消息数量
     * @param conversation_id 会话ID
     * @param user_id 用户ID
     * @return 未读消息数量
     */
    Integer get_unread_count(Long conversation_id, Long user_id);
} 