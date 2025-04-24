package com.example.back.pojo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Conversation {
    private Integer id;
    private Integer user_low_id;
    private Integer user_high_id;
    private byte[] last_message;
    private String last_message_display; // 用于前端显示的消息内容
    private String last_message_type;    // 消息类型：text 或 image
    private LocalDateTime last_message_time;
    private Integer low_unread;
    private Integer high_unread;
    
    // 非数据库字段，用于返回给前端
    private User target_user;
    private Message last_message_obj;
    private Integer unread_count;
    
    // 为了兼容性添加的getter和setter
    public void setLast_message(String message) {
        if (message != null) {
            this.last_message = message.getBytes();
            this.last_message_display = message;
        }
    }
    
    public String getLast_message() {
        if (this.last_message != null) {
            return new String(this.last_message);
        }
        return null;
    }
} 