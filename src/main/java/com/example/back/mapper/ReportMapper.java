package com.example.back.mapper;

import com.example.back.pojo.Report;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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

    // 新增方法：插入举报数据
    @Insert("INSERT INTO report (reporter_id, report_reason, content_type, report_content_id) " +
            "VALUES (#{report.reporter_id}, #{report.report_reason}, #{report.content_type}, #{report.report_content_id})")
    void insertReport(@Param("report") Report report);
}