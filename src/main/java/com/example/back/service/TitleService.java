package com.example.back.service;

import com.example.back.pojo.Title;
import java.util.List;

public interface TitleService {
    List<Title> findAll();
    Title findByTitle(String title);
    int insertTitle(String title, String value);
    int deleteTitle(Integer id);
}
