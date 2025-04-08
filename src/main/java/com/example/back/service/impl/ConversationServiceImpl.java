package com.example.back.service.impl;

import com.example.back.mapper.ConversationMapper;
import com.example.back.mapper.UserMapper;
import com.example.back.pojo.Conversation;
import com.example.back.pojo.User;
import com.example.back.service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ConversationServiceImpl implements ConversationService {
    @Autowired
    private ConversationMapper conversationMapper;
    
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<Conversation> get_conversations(Integer user_id) {
        List<Conversation> conversations = conversationMapper.get_conversations_by_user_id(user_id);
        for (Conversation conversation : conversations) {
            set_target_user(conversation, user_id);
        }
        return conversations;
    }

    @Override
    public Conversation get_conversation(Integer user_id, Integer target_user_id) {
        Integer user_low_id = Math.min(user_id, target_user_id);
        Integer user_high_id = Math.max(user_id, target_user_id);
        
        Conversation conversation = conversationMapper.get_conversation_by_user_ids(user_low_id, user_high_id);
        if (conversation != null) {
            set_target_user(conversation, user_id);
        }
        return conversation;
    }

    @Override
    public Conversation create_conversation(Integer user_id, Integer target_user_id) {
        Integer user_low_id = Math.min(user_id, target_user_id);
        Integer user_high_id = Math.max(user_id, target_user_id);
        
        Conversation conversation = new Conversation();
        conversation.setUser_low_id(user_low_id);
        conversation.setUser_high_id(user_high_id);
        conversation.setLast_message("");
        conversation.setLast_message_time(LocalDateTime.now());
        conversation.setLow_unread(0);
        conversation.setHigh_unread(0);
        
        conversationMapper.create_conversation(conversation);
        set_target_user(conversation, user_id);
        return conversation;
    }

    @Override
    public void update_last_message(Conversation conversation) {
        conversationMapper.update_last_message(conversation);
    }

    @Override
    public void increment_unread(Integer conversation_id, Integer user_id) {
        Conversation conversation = conversationMapper.get_conversations_by_user_id(user_id).stream()
                .filter(c -> c.getId().equals(conversation_id))
                .findFirst()
                .orElse(null);
                
        if (conversation != null) {
            if (user_id.equals(conversation.getUser_low_id())) {
                conversationMapper.increment_low_unread(conversation_id);
            } else {
                conversationMapper.increment_high_unread(conversation_id);
            }
        }
    }

    @Override
    public void clear_unread(Integer conversation_id, Integer user_id) {
        Conversation conversation = conversationMapper.get_conversations_by_user_id(user_id).stream()
                .filter(c -> c.getId().equals(conversation_id))
                .findFirst()
                .orElse(null);
                
        if (conversation != null) {
            if (user_id.equals(conversation.getUser_low_id())) {
                conversationMapper.clear_low_unread(conversation_id);
            } else {
                conversationMapper.clear_high_unread(conversation_id);
            }
        }
    }

    private void set_target_user(Conversation conversation, Integer user_id) {
        Integer target_user_id = conversation.getUser_low_id().equals(user_id) ? 
                conversation.getUser_high_id() : conversation.getUser_low_id();
        User target_user = userMapper.getUserById(target_user_id);
        conversation.setTarget_user(target_user);
    }
} 