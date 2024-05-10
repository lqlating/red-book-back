package com.example.back.service;

import com.example.back.pojo.Comment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {
    List<Comment> getComment(Integer article_id);
}
