package com.example.back.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Report {
    private Integer reportId;          // 对应数据库 report_id
    private Integer reporterId;        // 对应数据库 reporter_id
    private String reportReason;       // 对应数据库 report_reason
    private String contentType;        // 对应数据库 content_type
    private Integer reportContentId;   // 对应数据库 report_content_id
    private Object associatedData;
}