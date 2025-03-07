package com.example.back.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    private Integer cart_id;      // 对应数据库中的 cart_id
    private Integer book_id;      // 对应数据库中的 book_id
    private Integer owner_id;     // 对应数据库中的 owner_id
}