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
     * @param conversation_id 会话ID
     * @param page 页码，默认1
     * @param page_size 每页数量，默认20
     * @return 消息列表
     */
    @GetMapping("/messages/{conversation_id}")
    public Result getMessages(@PathVariable Long conversation_id,
                            @RequestParam(defaultValue = "1") Integer page,
                            @RequestParam(defaultValue = "20") Integer page_size) {
        List<Message> messages = messageService.get_messages(conversation_id, page, page_size);
        return Result.success(messages);
    }

    /**
     * 发送文本消息
     * @param conversation_id 会话ID
     * @param sender_id 发送者ID
     * @param content 消息内容
     * @return 发送的消息
     */
    @PostMapping("/messages/text")
    public Result sendTextMessage(@RequestParam Long conversation_id,
                                @RequestParam Long sender_id,
                                @RequestParam String content) {
        Message message = messageService.send_text_message(conversation_id, sender_id, content);
        return Result.success(message);
    }

    /**
     * 发送图片消息
     * @param conversation_id 会话ID
     * @param sender_id 发送者ID
     * @param image 图片文件
     * @return 发送的消息
     */
    @PostMapping("/messages/image")
    public Result sendImageMessage(@RequestParam Long conversation_id,
                                 @RequestParam Long sender_id,
                                 @RequestParam MultipartFile image) {
        Message message = messageService.send_image_message(conversation_id, sender_id, image);
        return Result.success(message);
    }

    /**
     * 发送Base64格式的图片消息
     * @param conversation_id 会话ID
     * @param sender_id 发送者ID
     * @param base64Image Base64格式的图片数据
     * @return 发送的消息
     */
    @PostMapping("/messages/base64-image")
    public Result sendBase64ImageMessage(@RequestParam Long conversation_id,
                                       @RequestParam Long sender_id,
                                       @RequestParam String base64Image) {
        Message message = messageService.send_base64_image_message(conversation_id, sender_id, base64Image);
        return Result.success(message);
    }

    /**
     * 标记消息为已读
     * @param conversation_id 会话ID
     * @param reader_id 阅读者ID
     * @return 操作结果
     */
    @PutMapping("/messages/read")
    public Result markMessagesAsRead(@RequestParam Long conversation_id,
                                   @RequestParam Long reader_id) {
        messageService.mark_messages_as_read(conversation_id, reader_id);
        return Result.success();
    }

    /**
     * 获取未读消息数量
     * @param conversation_id 会话ID
     * @param user_id 用户ID
     * @return 未读消息数量
     */
    @GetMapping("/messages/unread")
    public Result getUnreadCount(@RequestParam Long conversation_id,
                               @RequestParam Long user_id) {
        Integer count = messageService.get_unread_count(conversation_id, user_id);
        return Result.success(count);
    }
} 