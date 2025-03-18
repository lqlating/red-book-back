package com.example.back.mapper;

import com.example.back.pojo.Report;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface ReportMapper {
    // 移除所有的@Select注解，只保留方法声明
    List<Report> getReportsByArticleType();
    List<Report> getReportsByBookType();
    List<Report> getReportsByCommentType();
    List<Report> getReportsByUserType();
}