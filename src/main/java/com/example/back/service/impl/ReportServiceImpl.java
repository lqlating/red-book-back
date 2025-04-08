package com.example.back.service.impl;

import com.example.back.mapper.ReportMapper;
import com.example.back.pojo.Report;
import com.example.back.service.ReportService;
import com.example.back.service.ArticleService;
import com.example.back.service.CommentService;
import com.example.back.service.BookService;
import com.example.back.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private ReportMapper reportMapper;

    @Autowired
    private ArticleService articleService;  // 注入ArticleService

    @Autowired
    private CommentService commentService;  // 注入CommentService

    @Autowired
    private BookService bookService;        // 注入BookService

    @Autowired
    private UserService userService;        // 注入UserService

    @Override
    public List<Report> getReportsByArticleType() {
        return reportMapper.getReportsByArticleType();
    }

    @Override
    public List<Report> getReportsByBookType() {
        return reportMapper.getReportsByBookType();
    }

    @Override
    public List<Report> getReportsByCommentType() {
        return reportMapper.getReportsByCommentType();
    }

    @Override
    public List<Report> getReportsByUserType() {
        return reportMapper.getReportsByUserType();
    }

    @Override
    public List<Report> getReportsByContentType(String contentType) {
        List<Report> reports = reportMapper.getReportsByContentType(contentType);
        if (reports == null || reports.isEmpty()) {
            return reports;
        }

        // 根据 contentType 查询关联表中的数据
        for (Report report : reports) {
            if (report == null) continue;

            if ("article".equals(contentType)) {
                report.setAssociated_data(articleService.getArticleById(report.getReport_content_id()));
            } else if ("comment".equals(contentType)) {
                report.setAssociated_data(commentService.getCommentById(report.getReport_content_id()));
            } else if ("book".equals(contentType)) {
                report.setAssociated_data(bookService.getBookById(report.getReport_content_id()));
            } else if ("user".equals(contentType)) {
                report.setAssociated_data(userService.getUserById(report.getReport_content_id()));
            }
        }
        return reports;
    }

    @Override
    @Transactional
    public void addReport(Report report) {
        reportMapper.insertReport(report);
    }
}