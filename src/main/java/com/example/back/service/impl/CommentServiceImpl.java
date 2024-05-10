package com.example.back.service.impl;

import com.example.back.mapper.CommentMapper;
import com.example.back.pojo.Comment;
import com.example.back.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;


    @Override
    public List<Comment> getComment(Integer article_id) {
        return commentMapper.getComment(article_id);
    }
}
