package com.example.back.pojo;

import lombok.Data;

@Data
public class ConversationRequest {
    private Long user_id;
    private Long target_user_id;
} 