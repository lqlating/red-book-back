package com.example.back.controller;

import com.example.back.pojo.Conversation;
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
     * @param userId 用户ID
     * @return 会话列表
     */
    @GetMapping("/conversations/{userId}")
    public Result getConversations(@PathVariable Long userId) {
        List<Conversation> conversations = conversationService.getConversations(userId);
        return Result.success(conversations);
    }

    /**
     * 创建新会话
     * @param userId 当前用户ID
     * @param targetUserId 目标用户ID
     * @return 创建的会话信息
     */
    @PostMapping("/conversations")
    public Result createConversation(@RequestParam Long userId, @RequestParam Long targetUserId) {
        Conversation conversation = conversationService.createConversation(userId, targetUserId);
        return Result.success(conversation);
    }

    /**
     * 获取会话中的消息列表
     * @param conversationId 会话ID
     * @param page 页码，默认1
     * @param pageSize 每页数量，默认20
     * @return 消息列表
     */
    @GetMapping("/conversations/{conversationId}/messages")
    public Result getMessages(@PathVariable Long conversationId,
                            @RequestParam(defaultValue = "1") Integer page,
                            @RequestParam(defaultValue = "20") Integer pageSize) {
        List<Message> messages = conversationService.getMessages(conversationId, page, pageSize);
        return Result.success(messages);
    }

    /**
     * 发送文本消息
     * @param conversationId 会话ID
     * @param senderId 发送者ID
     * @param content 消息内容
     * @return 发送的消息
     */
    @PostMapping("/conversations/{conversationId}/messages/text")
    public Result sendTextMessage(@PathVariable Long conversationId,
                                @RequestParam Long senderId,
                                @RequestParam String content) {
        Message message = conversationService.sendTextMessage(conversationId, senderId, content);
        return Result.success(message);
    }

    /**
     * 发送图片消息
     * @param conversationId 会话ID
     * @param senderId 发送者ID
     * @param image 图片文件
     * @return 发送的消息
     */
    @PostMapping("/conversations/{conversationId}/messages/image")
    public Result sendImageMessage(@PathVariable Long conversationId,
                                 @RequestParam Long senderId,
                                 @RequestParam MultipartFile image) {
        Message message = conversationService.sendImageMessage(conversationId, senderId, image);
        return Result.success(message);
    }

    /**
     * 标记会话中的消息为已读
     * @param conversationId 会话ID
     * @param readerId 阅读者ID
     * @return 操作结果
     */
    @PutMapping("/conversations/{conversationId}/read")
    public Result markMessagesAsRead(@PathVariable Long conversationId,
                                   @RequestParam Long readerId) {
        conversationService.markMessagesAsRead(conversationId, readerId);
        return Result.success();
    }

    /**
     * 获取会话中的未读消息数量
     * @param conversationId 会话ID
     * @param userId 用户ID
     * @return 未读消息数量
     */
    @GetMapping("/conversations/{conversationId}/unread")
    public Result getUnreadCount(@PathVariable Long conversationId,
                               @RequestParam Long userId) {
        Integer count = conversationService.getUnreadCount(conversationId, userId);
        return Result.success(count);
    }

    /**
     * 获取用户所有会话的未读消息总数
     * @param userId 用户ID
     * @return 未读消息总数
     */
    @GetMapping("/conversations/total-unread/{userId}")
    public Result getTotalUnreadCount(@PathVariable Long userId) {
        Integer count = conversationService.getTotalUnreadCount(userId);
        return Result.success(count);
    }
} 