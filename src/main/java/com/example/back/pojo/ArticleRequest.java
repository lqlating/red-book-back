package com.example.back.pojo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleRequest {
    private String imgUrl;
    private String txtType;
    private Integer authorId;
    private String content;
    private String title;
    private Integer likeCount;
    private Integer starCount;
    private String publicationTime;
    private String address;
}
