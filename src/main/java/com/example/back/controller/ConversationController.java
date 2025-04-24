package com.example.back.controller;

import com.example.back.pojo.Conversation;
import com.example.back.pojo.Message;
import com.example.back.pojo.Result;
import com.example.back.service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class ConversationController {
    @Autowired
    private ConversationService conversationService;

    /**
     * 获取用户的所有会话列表
     * @param user_id 用户ID
     * @return 会话列表
     */
    @GetMapping("/conversations/{user_id}")
    public Result getConversations(@PathVariable Long user_id) {
        List<Conversation> conversations = conversationService.get_conversations(user_id);
        return Result.success(conversations);
    }

    /**
     * 创建新会话
     * @param user_id 当前用户ID
     * @param target_user_id 目标用户ID
     * @return 创建的会话信息
     */
    @PostMapping("/conversations")
    public Result createConversation(@RequestParam Long user_id, @RequestParam Long target_user_id) {
        Conversation conversation = conversationService.create_conversation(user_id, target_user_id);
        return Result.success(conversation);
    }

    /**
     * 获取会话中的消息列表
     * @param conversation_id 会话ID
     * @param page 页码，默认1
     * @param page_size 每页数量，默认20
     * @return 消息列表
     */
    @GetMapping("/conversations/{conversation_id}/messages")
    public Result getMessages(@PathVariable Long conversation_id,
                            @RequestParam(defaultValue = "1") Integer page,
                            @RequestParam(defaultValue = "20") Integer page_size) {
        List<Message> messages = conversationService.get_messages(conversation_id, page, page_size);
        return Result.success(messages);
    }

    /**
     * 发送文本消息
     * @param conversation_id 会话ID
     * @param sender_id 发送者ID
     * @param content 消息内容
     * @return 发送的消息
     */
    @PostMapping("/conversations/{conversation_id}/messages/text")
    public Result sendTextMessage(@PathVariable Long conversation_id,
                                @RequestParam Long sender_id,
                                @RequestParam String content) {
        Message message = conversationService.send_text_message(conversation_id, sender_id, content);
        return Result.success(message);
    }

    /**
     * 发送图片消息
     * @param conversation_id 会话ID
     * @param sender_id 发送者ID
     * @param image 图片文件
     * @return 发送的消息
     */
    @PostMapping("/conversations/{conversation_id}/messages/image")
    public Result sendImageMessage(@PathVariable Long conversation_id,
                                 @RequestParam Long sender_id,
                                 @RequestParam MultipartFile image) {
        Message message = conversationService.send_image_message(conversation_id, sender_id, image);
        return Result.success(message);
    }

    /**
     * 标记会话中的消息为已读
     * @param conversation_id 会话ID
     * @param reader_id 阅读者ID
     * @return 操作结果
     */
    @PutMapping("/conversations/{conversation_id}/read")
    public Result markMessagesAsRead(@PathVariable Long conversation_id,
                                   @RequestParam Long reader_id) {
        conversationService.mark_messages_as_read(conversation_id, reader_id);
        return Result.success();
    }

    /**
     * 获取会话中的未读消息数量
     * @param conversation_id 会话ID
     * @param user_id 用户ID
     * @return 未读消息数量
     */
    @GetMapping("/conversations/{conversation_id}/unread")
    public Result getUnreadCount(@PathVariable Long conversation_id,
                               @RequestParam Long user_id) {
        Integer count = conversationService.get_unread_count(conversation_id, user_id);
        return Result.success(count);
    }

    /**
     * 获取用户所有会话的未读消息总数
     * @param user_id 用户ID
     * @return 未读消息总数
     */
    @GetMapping("/conversations/total-unread/{user_id}")
    public Result getTotalUnreadCount(@PathVariable Long user_id) {
        Integer count = conversationService.get_total_unread_count(user_id);
        return Result.success(count);
    }
} 