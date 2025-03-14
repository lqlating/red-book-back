package com.example.back.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    private String img_url;
    private String txt_type;
    private Integer author_id;
    private String content;
    private String title;

    private Integer article_id;
    private String publication_time;
    private String address;
    private Integer star_count;
    private Integer like_count;

    // 新增 img 字段，用于存储图片的二进制数据
    private byte[] img;

    // 新增字段
    private Integer is_review;
    private Integer is_banned;
}