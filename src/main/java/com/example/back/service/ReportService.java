package com.example.back.service;

import com.example.back.pojo.Report;

import java.util.List;

public interface ReportService {
    List<Report> getReportsByArticleType();
    List<Report> getReportsByBookType();
    List<Report> getReportsByCommentType();
    List<Report> getReportsByUserType();
    List<Report> getReportsByContentType(String contentType);
    void addReport(Report report);
    
    /**
     * 根据举报ID删除举报
     * @param reportId 举报ID
     * @return 删除成功返回true，否则返回false
     */
    boolean deleteReportById(Integer reportId);
    
    /**
     * 根据内容类型和内容ID删除举报
     * @param contentType 内容类型（article, comment, book, user）
     * @param contentId 内容ID
     * @return 删除成功返回true，否则返回false
     */
    boolean deleteReportByContentTypeAndId(String contentType, Integer contentId);
}