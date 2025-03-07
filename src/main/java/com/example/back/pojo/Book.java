package com.example.back.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private Integer book_id;        // 对应数据库中的 book_id
    private byte[] book_img;        // 对应数据库中的 book_img（原始图片字节数据）
    private String book_seller_id;  // 对应数据库中的 book_seller_id
    private Integer book_price;     // 对应数据库中的 book_price
    private String book_writer;     // 对应数据库中的 book_writer
    private String book_title;      // 对应数据库中的 book_title
    private String book_type;       // 对应数据库中的 book_type
    private String book_img_url;    // 用于存储 Base64 格式的图片
    private String book_descripe;
}
