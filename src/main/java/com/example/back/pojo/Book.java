package com.example.back.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private Integer book_id;        // 对应数据库中的 book_id
    private String book_img;        // 对应数据库中的 book_img
    private String book_seller_id;  // 对应数据库中的 book_seller_id
    private Integer book_price;     // 对应数据库中的 book_price
    private String book_writer;     // 对应数据库中的 book_writer
    private String book_title;      // 对应数据库中的 book_title
}
