package com.example.back.service;

import com.example.back.pojo.Message;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MessageService {
    List<Message> get_messages(Integer conversation_id, Integer page, Integer page_size);
    Message send_text_message(Integer conversation_id, Integer sender_id, String content);
    Message send_image_message(Integer conversation_id, Integer sender_id, MultipartFile image);
    void mark_messages_as_read(Integer conversation_id, Integer reader_id);
    Integer get_unread_count(Integer conversation_id, Integer user_id);
} 