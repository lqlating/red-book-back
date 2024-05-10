package com.example.back.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    private String img_url;
    private Integer like_count;
    private String txt_type;
    private Integer author_id;
    private String content;
    private  Integer article_id;
    private  String title;
    private String publication_time;
    private String address;
    private String star_count;
}