package com.example.back.mapper;

import com.example.back.pojo.Title;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TitleMapper {

    // 查询所有 title 记录
    List<Title> findAll();

    // 根据 title 关键字查询
    Title findByTitle(String title);

    // 插入一条记录
    int insertTitle(@Param("title") String title, @Param("value") String value);

    // 删除记录
    int deleteTitle(@Param("id") Integer id);
}
