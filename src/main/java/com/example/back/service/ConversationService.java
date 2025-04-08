package com.example.back.service;

import com.example.back.pojo.Conversation;
import java.util.List;

public interface ConversationService {
    List<Conversation> get_conversations(Integer user_id);
    Conversation get_conversation(Integer user_id, Integer target_user_id);
    Conversation create_conversation(Integer user_id, Integer target_user_id);
    void update_last_message(Conversation conversation);
    void increment_unread(Integer conversation_id, Integer user_id);
    void clear_unread(Integer conversation_id, Integer user_id);
} 