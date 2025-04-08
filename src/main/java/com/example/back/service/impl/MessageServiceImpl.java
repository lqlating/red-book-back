package com.example.back.service.impl;

import com.example.back.mapper.MessageMapper;
import com.example.back.mapper.UserMapper;
import com.example.back.pojo.Message;
import com.example.back.pojo.User;
import com.example.back.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageMapper messageMapper;
    
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<Message> get_messages(Integer conversation_id, Integer page, Integer page_size) {
        Integer offset = (page - 1) * page_size;
        List<Message> messages = messageMapper.get_messages_by_conversation_id(conversation_id, offset, page_size);
        for (Message message : messages) {
            set_sender(message);
        }
        return messages;
    }

    @Override
    public Message send_text_message(Integer conversation_id, Integer sender_id, String content) {
        Message message = new Message();
        message.setConversation_id(conversation_id);
        message.setSender_id(sender_id);
        message.setContent(content);
        message.setImage_url(null);
        message.setCreated_at(LocalDateTime.now());
        message.setIs_read(false);
        
        messageMapper.create_message(message);
        set_sender(message);
        return message;
    }

    @Override
    public Message send_image_message(Integer conversation_id, Integer sender_id, MultipartFile image) {
        // TODO: 实现图片上传逻辑，获取实际的图片URL
        String image_url = "placeholder_url";
        
        Message message = new Message();
        message.setConversation_id(conversation_id);
        message.setSender_id(sender_id);
        message.setContent(null);
        message.setImage_url(image_url);
        message.setCreated_at(LocalDateTime.now());
        message.setIs_read(false);
        
        messageMapper.create_message(message);
        set_sender(message);
        return message;
    }

    @Override
    public void mark_messages_as_read(Integer conversation_id, Integer reader_id) {
        messageMapper.mark_messages_as_read(conversation_id, reader_id);
    }

    @Override
    public Integer get_unread_count(Integer conversation_id, Integer user_id) {
        return messageMapper.get_unread_count(conversation_id, user_id);
    }

    private void set_sender(Message message) {
        User sender = userMapper.getUserById(message.getSender_id());
        message.setSender(sender);
    }
} 