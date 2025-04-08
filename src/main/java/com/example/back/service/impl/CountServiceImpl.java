package com.example.back.service.impl;

import com.example.back.mapper.CountMapper;
import com.example.back.service.CountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountServiceImpl implements CountService {
    @Autowired
    private CountMapper countMapper;
    @Override
    public int getCommentCount(Integer article_id) {
        return countMapper.getCommentCount(article_id);
    }
}