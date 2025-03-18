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
}