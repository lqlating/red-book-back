package com.example.back.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Report {
    private Integer report_id;          // 对应数据库 report_id
    private Integer reporter_id;        // 对应数据库 reporter_id
    private String report_reason;       // 对应数据库 report_reason
    private String content_type;        // 对应数据库 content_type
    private Integer report_content_id;  // 对应数据库 report_content_id
    private Object associated_data;     // 关联数据，使用下划线命名
}