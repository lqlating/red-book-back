package com.example.back.mapper;

import com.example.back.pojo.Report;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface ReportMapper {
    List<Report> getReportsByContentType(String contentType);
    List<Report> getReportsByArticleType();
    List<Report> getReportsByBookType();
    List<Report> getReportsByCommentType();
    List<Report> getReportsByUserType();
    
    // 添加新的查询方法
    Object getArticleById(Integer id);
    Object getCommentById(Integer id);
    Object getBookById(Integer id);
    Object getUserById(Integer id);
}