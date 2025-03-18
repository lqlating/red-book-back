package com.example.back.service.impl;

import com.example.back.mapper.ReportMapper;
import com.example.back.pojo.Report;
import com.example.back.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private ReportMapper reportMapper;

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
}