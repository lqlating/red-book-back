package com.example.back.pojo;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String username;
    private String password;
    private String avatar;
    private String email;
    private Integer id;
    private String gender;
    private String introduction;
    private String subscript;
    private String fans;

    // 新增字段
    private LocalDateTime ban_until;
    private Integer is_banned;

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