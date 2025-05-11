package com.example.back.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest {

    private Integer user_id;
    private String content_type;
    private String operation_type;

    // Getters and Setters
}
