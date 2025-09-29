package com.example.back.pojo;

import lombok.Data;

@Data
public class UserUpdateRequest {
    private Integer id;              // 用户ID（必需）
    private String username;         // 用户名（可选）
    private String avatar;           // 头像（可选）
    private String email;            // 邮箱（可选）
    private String gender;           // 性别（可选）
    private String introduction;     // 简介（可选）
} 