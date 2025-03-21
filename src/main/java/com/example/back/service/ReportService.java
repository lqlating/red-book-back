package com.example.back.service;

import com.example.back.pojo.Report;

import java.util.List;

public interface ReportService {
    List<Report> getReportsByArticleType();
    List<Report> getReportsByBookType();
    List<Report> getReportsByCommentType();
    List<Report> getReportsByUserType();
    List<Report> getReportsByContentType(String contentType);
}