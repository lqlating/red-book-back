package com.example.back.pojo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Message {
    private Integer id;
    private Integer conversation_id;
    private Integer sender_id;
    private String content;
    private String image_url;
    private LocalDateTime created_at;
    private Boolean is_read;
    
    // 非数据库字段，用于返回给前端
    private User sender;
} 