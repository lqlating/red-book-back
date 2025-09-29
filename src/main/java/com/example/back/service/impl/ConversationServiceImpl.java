package com.example.back.service.impl;

import com.example.back.mapper.ConversationMapper;
import com.example.back.mapper.MessageMapper;
import com.example.back.mapper.UserMapper;
import com.example.back.pojo.Conversation;
import com.example.back.pojo.Message;
import com.example.back.pojo.User;
import com.example.back.service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

@Service
public class ConversationServiceImpl implements ConversationService {
    @Autowired
    private ConversationMapper conversationMapper;

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<Conversation> get_conversations(Long user_id) {
        List<Conversation> conversations = conversationMapper.get_conversations_by_user_id(user_id);
        for (Conversation conversation : conversations) {
            // 设置目标用户信息
            Long target_user_id;
            if (conversation.getUser_low_id().longValue() == user_id) {
                target_user_id = conversation.getUser_high_id().longValue();
            } else {
                target_user_id = conversation.getUser_low_id().longValue();
            }
            User target_user = userMapper.getUserById(target_user_id.intValue());
            
            // 转换头像为Base64
            if (target_user != null && target_user.getAvatar() != null) {
                String avatar_base64 = Base64.getEncoder().encodeToString(target_user.getAvatar());
                target_user.setAvatar_base64(avatar_base64);
                target_user.setAvatar(null); // 清空原始字节数组，避免传输
            }
            
            conversation.setTarget_user(target_user);
            
            // 设置未读消息数量
            Integer unread_count;
            if (conversation.getUser_low_id().longValue() == user_id) {
                unread_count = conversation.getLow_unread();
            } else {
                unread_count = conversation.getHigh_unread();
            }
            conversation.setUnread_count(unread_count);
        }
        return conversations;
    }

    @Override
    public Conversation create_conversation(Long user_id, Long target_user_id) {
        // 确保 user_id 和 target_user_id 的顺序一致
        Long user_low_id = Math.min(user_id, target_user_id);
        Long user_high_id = Math.max(user_id, target_user_id);
        
        // 检查是否已存在会话
        Conversation existing_conversation = conversationMapper.get_conversation_by_user_ids(user_low_id, user_high_id);
        if (existing_conversation != null) {
            return existing_conversation;
        }
        
        // 创建新会话
        Conversation conversation = new Conversation();
        conversation.setUser_low_id(user_low_id.intValue());
        conversation.setUser_high_id(user_high_id.intValue());
        conversation.setLow_unread(0);
        conversation.setHigh_unread(0);
        conversation.setLast_message("");
        conversation.setLast_message_time(LocalDateTime.now());
        conversationMapper.create_conversation(conversation);
        
        return conversation;
    }

    @Override
    public List<Message> get_messages(Long conversation_id, Integer page, Integer page_size) {
        Integer offset = (page - 1) * page_size;
        return messageMapper.get_messages_by_conversation_id(conversation_id.intValue(), offset, page_size);
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
        
        // 获取最新消息并更新会话
        Message latest_message = messageMapper.get_latest_message(conversation_id.intValue());
        Conversation conversation = conversationMapper.get_conversation_by_id(conversation_id);
        conversation.setLast_message(latest_message.getContent_text());
        conversation.setLast_message_time(latest_message.getCreated_at());
        conversationMapper.update_last_message(conversation);
        
        // 增加未读消息计数
        if (conversation.getUser_low_id().longValue() == sender_id) {
            conversationMapper.increment_high_unread(conversation_id);
        } else {
            conversationMapper.increment_low_unread(conversation_id);
        }
        
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        messageMapper.create_message(message);
        
        // 获取最新消息并更新会话
        Message latest_message = messageMapper.get_latest_message(conversation_id.intValue());
        Conversation conversation = conversationMapper.get_conversation_by_id(conversation_id);
        conversation.setLast_message("[图片]");
        conversation.setLast_message_time(latest_message.getCreated_at());
        conversationMapper.update_last_message(conversation);
        
        // 增加未读消息计数
        if (conversation.getUser_low_id().longValue() == sender_id) {
            conversationMapper.increment_high_unread(conversation_id);
        } else {
            conversationMapper.increment_low_unread(conversation_id);
        }
        
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

    @Override
    public Integer get_total_unread_count(Long user_id) {
        List<Conversation> conversations = conversationMapper.get_conversations_by_user_id(user_id);
        int total = 0;
        for (Conversation conversation : conversations) {
            if (conversation.getUser_low_id().longValue() == user_id) {
                total += conversation.getLow_unread();
            } else {
                total += conversation.getHigh_unread();
            }
        }
        return total;
    }
} 