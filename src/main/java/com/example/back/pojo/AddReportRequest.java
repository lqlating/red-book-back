package com.example.back.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddReportRequest {
    private Integer reporter_id;        // 举报者ID
    private String report_reason;       // 举报原因
    private String content_type;        // 内容类型
    private Integer report_content_id;  // 被举报内容ID
} 