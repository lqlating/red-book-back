package com.example.back.pojo;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String username;
    private String password;
    private byte[] avatar;        // 存储 BLOB 数据
    private String avatar_base64; // 用于存储转换后的 Base64 字符串
    private String email;
    private Integer id;
    private String gender;
    private String introduction;
    private String subscript;
    private String fans = "0";    // 设置初始值为0
    private  String account;
    // 新增字段
    private LocalDateTime ban_until = null;  // 设置初始值为null
    private Integer is_banned = 0;           // 设置初始值为0

    // 手动添加 setter 和 getter 方法
    public void setIsBanned(Integer is_banned) {
        this.is_banned = is_banned;
    }

    public Integer getIsBanned() {
        return is_banned;
    }

    public void setBanUntil(LocalDateTime ban_until) {
        this.ban_until = ban_until;
    }

    public LocalDateTime getBanUntil() {
        return ban_until;
    }
}