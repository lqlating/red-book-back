package com.example.back.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperationRequest {

    private Integer user_id;
    private Integer comment_id;
    private Integer article_id;
    private String operation_type;

    // Getters and Setters
}
