package com.example.back.pojo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Conversation {
    private Integer id;
    private Integer user_low_id;
    private Integer user_high_id;
    private String last_message;
    private LocalDateTime last_message_time;
    private Integer low_unread;
    private Integer high_unread;
    
    // 非数据库字段，用于返回给前端
    private User target_user;
    private Message last_message_obj;
} 