package com.example.back.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    private Long transaction_id;
    private Integer book_id;
    private Integer seller_id;
    private Integer buyer_id;
    private String status;
    private LocalDateTime create_time;
    private LocalDateTime update_time;
    
    // 关联的书籍信息
    private Book book;
} 