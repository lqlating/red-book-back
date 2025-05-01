package com.example.back.service.impl;

import com.example.back.mapper.CommentMapper;
import com.example.back.pojo.Comment;
import com.example.back.pojo.Result;
import com.example.back.pojo.User;
import com.example.back.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<Comment> getCommentBylikeCount(Integer article_id) {
        return commentMapper.getCommentBylikeCount(article_id);
    }

    @Override
    public List<Comment> getCommentByTime(Integer article_id) {
        return commentMapper.getCommentByTime(article_id);
    }

    @Override
    public int getReplyCountByCommentId(Integer comment_id) {
        return commentMapper.getReplyCountByCommentId(comment_id);
    }

    @Override
    public List<Comment> getCommentsByParentId(Integer parent_id) {
        return commentMapper.getCommentsByParentId(parent_id);
    }

    @Override
    public int getCommentCountByParentId(Integer parent_id) {
        return commentMapper.getCommentCountByParentId(parent_id);
    }

    @Override
    public List<User> findUserByCommentId(Integer commentId) {
        return commentMapper.findUserByCommentId(commentId);
    }

    @Override
    public Result addComment(Comment comment) {
        int result = commentMapper.addComment(comment);
        if (result > 0) {
            return Result.success();
        }
        return Result.error("Failed to add comment");
    }

    @Override
    public List<Comment> getCommentsByUserId(Integer userId) {
        return commentMapper.getCommentsByUserId(userId);
    }

    @Override
    public List<Comment> findCommentByCommentId(Integer commentId) {
        return commentMapper.findCommentByCommentId(commentId);
    }

    @Override
    public List<Comment> getBannedComments() {
        return commentMapper.getBannedComments();
    }

    @Override
    public boolean banCommentById(Integer commentId) {
        if (commentId == null) {
            return false;
        }
        int result = commentMapper.banCommentById(commentId);
        return result > 0;
    }

    @Override
    public boolean unbanCommentById(Integer commentId) {
        if (commentId == null) {
            return false;
        }
        int result = commentMapper.unbanCommentById(commentId);
        return result > 0;
    }

    @Override
    public boolean deleteComment(Integer comment_id) {
        if (comment_id == null) {
            return false;
        }
        int result = commentMapper.deleteComment(comment_id);
        return result > 0;
    }

    @Override
    public Object getCommentById(Integer id) {
        return commentMapper.getCommentById(id);
    }
}