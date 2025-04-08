package com.example.back.service.impl;

import com.example.back.mapper.TitleMapper;
import com.example.back.pojo.Title;
import com.example.back.service.TitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TitleServiceImpl implements TitleService {

    @Autowired
    private TitleMapper titleMapper;

    @Override
    public List<Title> findAll() {
        return titleMapper.findAll();
    }

    @Override
    public Title findByTitle(String title) {
        return titleMapper.findByTitle(title);
    }

    @Override
    public int insertTitle(String title, String value) {
        return titleMapper.insertTitle(title, value);
    }

    @Override
    public int deleteTitle(Integer id) {
        return titleMapper.deleteTitle(id);
    }
}