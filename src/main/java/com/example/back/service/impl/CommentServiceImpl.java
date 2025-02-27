package com.example.back.service.impl;

import com.example.back.mapper.CommentMapper;
import com.example.back.pojo.Comment;
import com.example.back.pojo.Result;
import com.example.back.pojo.User;
import com.example.back.service.CommentService;
import com.example.back.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserService userService; // 注入 UserService

    @Override
    public List<Comment> getCommentBylikeCount(Integer article_id) {
        return commentMapper.getCommentBylikeCount(article_id);
    }

    @Override
    public List<Comment> getCommentByTime(Integer article_id) {
        return commentMapper.getCommentByTime(article_id);
    }

    @Override
    public int getReplyCountByCommentId(Integer commentId) {
        return commentMapper.getReplyCountByCommentId(commentId);
    }

    @Override
    public List<Comment> getCommentsByParentId(Integer parentId) {
        return commentMapper.getCommentsByParentId(parentId);
    }

    @Override
    public int getCommentCountByParentId(Integer parentId) {
        return commentMapper.getCommentCountByParentId(parentId);
    }

    @Override
    public List<User> findUserByCommentId(Integer commentId) {
        Integer userId = commentMapper.findUserIdByCommentId(commentId);
        if (userId != null) {
            return userService.search(userId);
        }
        return null;
    }

    @Override
    public Result addComment(Comment comment) {
        try {
            // 插入评论并获取插入行数
            int rows = commentMapper.insertComment(comment);
            if (rows > 0) {
                return Result.success();
            } else {
                return Result.error("评论添加失败");
            }
        } catch (Exception e) {
            return Result.error("评论添加失败: " + e.getMessage());
        }
    }

    @Override
    public List<Comment> getCommentsByUserId(Integer userId) {
        return commentMapper.findCommentsByUserId(userId);
    }

    // 根据 comment_id 查找评论
    @Override
    public List<Comment> findCommentByCommentId(Integer commentId) {
        return commentMapper.findCommentByCommentId(commentId);
    }
}
