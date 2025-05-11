package com.example.back.mapper;

import com.example.back.pojo.Report;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Delete;
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
    
    // 新增方法：根据report_id删除举报数据
    @Delete("DELETE FROM report WHERE report_id = #{reportId}")
    int deleteReportById(@Param("reportId") Integer reportId);
    
    // 新增方法：根据内容类型和内容ID删除举报数据
    @Delete("DELETE FROM report WHERE content_type = #{contentType} AND report_content_id = #{contentId}")
    int deleteReportByContentTypeAndId(@Param("contentType") String contentType, @Param("contentId") Integer contentId);
}