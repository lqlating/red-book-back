package com.example.back.service;

import org.springframework.stereotype.Service;

@Service
public interface CountService {
    int getCommentCount(Integer article_id);
}
