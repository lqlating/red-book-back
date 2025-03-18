package com.example.back.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Report {
    private Integer reportId;
    private Integer reporterId;
    private String reportReason;
    private String contentType;
    private Integer reportContentId;
}