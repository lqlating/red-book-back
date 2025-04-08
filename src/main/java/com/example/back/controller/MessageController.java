package com.example.back.controller;

import com.example.back.pojo.Message;
import com.example.back.pojo.Result;
import com.example.back.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class MessageController {
    @Autowired
    private MessageService messageService;

    /**
     * 获取会话中的消息列表
     * @param conversationId 会话ID
     * @param page 页码，默认1
     * @param pageSize 每页数量，默认20
     * @return 消息列表
     */
    @GetMapping("/messages/{conversationId}")
    public Result getMessages(@PathVariable Long conversationId,
                            @RequestParam(defaultValue = "1") Integer page,
                            @RequestParam(defaultValue = "20") Integer pageSize) {
        List<Message> messages = messageService.getMessages(conversationId, page, pageSize);
        return Result.success(messages);
    }

    /**
     * 发送文本消息
     * @param conversationId 会话ID
     * @param senderId 发送者ID
     * @param content 消息内容
     * @return 发送的消息
     */
    @PostMapping("/messages/text")
    public Result sendTextMessage(@RequestParam Long conversationId,
                                @RequestParam Long senderId,
                                @RequestParam String content) {
        Message message = messageService.sendTextMessage(conversationId, senderId, content);
        return Result.success(message);
    }

    /**
     * 发送图片消息
     * @param conversationId 会话ID
     * @param senderId 发送者ID
     * @param image 图片文件
     * @return 发送的消息
     */
    @PostMapping("/messages/image")
    public Result sendImageMessage(@RequestParam Long conversationId,
                                 @RequestParam Long senderId,
                                 @RequestParam MultipartFile image) {
        Message message = messageService.sendImageMessage(conversationId, senderId, image);
        return Result.success(message);
    }

    /**
     * 标记消息为已读
     * @param conversationId 会话ID
     * @param readerId 阅读者ID
     * @return 操作结果
     */
    @PutMapping("/messages/read")
    public Result markMessagesAsRead(@RequestParam Long conversationId,
                                   @RequestParam Long readerId) {
        messageService.markMessagesAsRead(conversationId, readerId);
        return Result.success();
    }

    /**
     * 获取未读消息数量
     * @param conversationId 会话ID
     * @param userId 用户ID
     * @return 未读消息数量
     */
    @GetMapping("/messages/unread")
    public Result getUnreadCount(@RequestParam Long conversationId,
                               @RequestParam Long userId) {
        Integer count = messageService.getUnreadCount(conversationId, userId);
        return Result.success(count);
    }
} 