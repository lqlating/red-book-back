package com.example.back.service.impl;

import com.example.back.mapper.MessageMapper;
import com.example.back.mapper.UserMapper;
import com.example.back.mapper.ConversationMapper;
import com.example.back.pojo.Message;
import com.example.back.pojo.User;
import com.example.back.pojo.Conversation;
import com.example.back.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageMapper messageMapper;
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private ConversationMapper conversationMapper;

    @Override
    public List<Message> get_messages(Long conversation_id, Integer page, Integer page_size) {
        Integer offset = (page - 1) * page_size;
        List<Message> messages = messageMapper.get_messages_by_conversation_id(conversation_id.intValue(), offset, page_size);
        for (Message message : messages) {
            // 设置发送者信息
            set_sender(message);
            
            // 如果是图片消息，转换为Base64
            if ("image".equals(message.getMessage_type()) && message.getContent_image() != null) {
                String base64Image = Base64.getEncoder().encodeToString(message.getContent_image());
                message.setContent_image_base64(base64Image);
                message.setContent_image(null); // 清空原始图片数据
            }
        }
        return messages;
    }

    @Override
    public Message send_text_message(Long conversation_id, Long sender_id, String content) {
        Message message = new Message();
        message.setConversation_id(conversation_id.intValue());
        message.setSender_id(sender_id.intValue());
        message.setContent_text(content);
        message.setMessage_type("text");
        message.setCreated_at(LocalDateTime.now());
        message.setIs_read(false);
        
        messageMapper.create_message(message);
        
        // 更新会话的最后消息
        Conversation conversation = conversationMapper.get_conversation_by_id(conversation_id);
        conversation.setLast_message(content);
        conversation.setLast_message_type("text");
        conversation.setLast_message_time(message.getCreated_at());
        conversationMapper.update_last_message(conversation);
        
        // 增加未读消息计数
        if (conversation.getUser_low_id().longValue() == sender_id) {
            conversationMapper.increment_high_unread(conversation_id);
        } else {
            conversationMapper.increment_low_unread(conversation_id);
        }
        
        set_sender(message);
        return message;
    }

    @Override
    public Message send_image_message(Long conversation_id, Long sender_id, MultipartFile image) {
        Message message = new Message();
        message.setConversation_id(conversation_id.intValue());
        message.setSender_id(sender_id.intValue());
        message.setMessage_type("image");
        message.setCreated_at(LocalDateTime.now());
        message.setIs_read(false);
        
        // 处理图片数据
        try {
            message.setContent_image(image.getBytes());
            // 设置Base64表示
            String base64Image = Base64.getEncoder().encodeToString(image.getBytes());
            message.setContent_image_base64(base64Image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        messageMapper.create_message(message);
        
        // 更新会话的最后消息
        Conversation conversation = conversationMapper.get_conversation_by_id(conversation_id);
        conversation.setLast_message(message.getContent_image_base64()); // 存储Base64格式的图片
        conversation.setLast_message_type("image");
        conversation.setLast_message_time(message.getCreated_at());
        conversationMapper.update_last_message(conversation);
        
        // 增加未读消息计数
        if (conversation.getUser_low_id().longValue() == sender_id) {
            conversationMapper.increment_high_unread(conversation_id);
        } else {
            conversationMapper.increment_low_unread(conversation_id);
        }
        
        set_sender(message);
        return message;
    }

    // 新增方法：发送Base64格式的图片消息
    public Message send_base64_image_message(Long conversation_id, Long sender_id, String base64Image) {
        Message message = new Message();
        message.setConversation_id(conversation_id.intValue());
        message.setSender_id(sender_id.intValue());
        message.setMessage_type("image");
        message.setCreated_at(LocalDateTime.now());
        message.setIs_read(false);
        
        // 将Base64转换为字节数组
        byte[] imageBytes = Base64.getDecoder().decode(base64Image.split(",")[1]); // 移除 "data:image/jpeg;base64," 前缀
        message.setContent_image(imageBytes);
        message.setContent_image_base64(base64Image);
        
        messageMapper.create_message(message);
        
        // 更新会话的最后消息
        Conversation conversation = conversationMapper.get_conversation_by_id(conversation_id);
        conversation.setLast_message(base64Image); // 存储完整的Base64字符串
        conversation.setLast_message_type("image");
        conversation.setLast_message_time(message.getCreated_at());
        conversationMapper.update_last_message(conversation);
        
        // 增加未读消息计数
        if (conversation.getUser_low_id().longValue() == sender_id) {
            conversationMapper.increment_high_unread(conversation_id);
        } else {
            conversationMapper.increment_low_unread(conversation_id);
        }
        
        set_sender(message);
        return message;
    }

    @Override
    public void mark_messages_as_read(Long conversation_id, Long reader_id) {
        messageMapper.mark_messages_as_read(conversation_id.intValue(), reader_id.intValue());
        
        // 清除未读消息计数
        Conversation conversation = conversationMapper.get_conversation_by_id(conversation_id);
        if (conversation.getUser_low_id().longValue() == reader_id) {
            conversationMapper.clear_low_unread(conversation_id);
        } else {
            conversationMapper.clear_high_unread(conversation_id);
        }
    }

    @Override
    public Integer get_unread_count(Long conversation_id, Long user_id) {
        return messageMapper.get_unread_count(conversation_id.intValue(), user_id.intValue());
    }

    private void set_sender(Message message) {
        User sender = userMapper.getUserById(message.getSender_id());
        if (sender != null && sender.getAvatar() != null) {
            String avatar_base64 = Base64.getEncoder().encodeToString(sender.getAvatar());
            sender.setAvatar_base64(avatar_base64);
            sender.setAvatar(null); // 清空原始字节数组，避免传输
        }
        message.setSender(sender);
    }
} 