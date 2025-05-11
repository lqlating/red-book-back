package com.example.back.pojo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Message {
    private Integer id;
    private Integer conversation_id;
    private Integer sender_id;
    private String content_text;
    private byte[] content_image;
    private String content_image_base64; // 用于存储转换后的Base64图片
    private String message_type; // 'text' 或 'image'
    private LocalDateTime created_at;
    private Boolean is_read;
    
    // 非数据库字段，用于返回给前端
    private User sender;
    
    // 为了兼容旧代码，提供一些辅助方法
    public String getContent() {
        return content_text;
    }
    
    public void setContent(String content) {
        this.content_text = content;
        if (content != null) {
            this.message_type = "text";
        }
    }
    
    public String getImage_url() {
        return content_image_base64;
    }
    
    public void setImage_url(String imageUrl) {
        // 此方法保留兼容性，实际存储使用content_image
    }
} 