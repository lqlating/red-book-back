package com.example.back.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private String content;
    private Integer comment_id;
    private String parent_id;
    private Integer article_id;
    private Integer user_id;
    private  Integer reply_comment_id;
    private  Integer like_count;
    private String publish_time;

}
