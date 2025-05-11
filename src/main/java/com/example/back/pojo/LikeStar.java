package com.example.back.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LikeStar {
    private Integer user_id;
    private Integer target_id;
    private String content_type;
    private String operation_type;
    private Integer operation_id; // 自增字段
    // Getters and Setters
}
