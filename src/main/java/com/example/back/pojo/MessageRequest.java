package com.example.back.pojo;

import lombok.Data;

@Data
public class MessageRequest {
    private Long conversationId;
    private Long senderId;
    private String content;
    private String messageType;
} 