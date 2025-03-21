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
    public List<Comment> getCommentByTime(Integer articleId) {
        return commentMapper.getCommentByTime(articleId);
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
        // 假设这里有一个方法可以返回 List<User>
        // 这里仅作示例，实际需要根据业务逻辑实现
        return commentMapper.findUserByCommentId(commentId);
    }

    @Override
    public Result addComment(Comment comment) {
        commentMapper.insertComment(comment);
        return Result.success();
    }

    @Override
    public List<Comment> getCommentsByUserId(Integer userId) {
        return commentMapper.findCommentsByUserId(userId);
    }

    @Override
    public List<Comment> findCommentByCommentId(Integer commentId) {
        return commentMapper.findCommentByCommentId(commentId);
    }

    // 新增方法：获取所有 is_banned 为 1 的评论数据
    @Override
    public List<Comment> getBannedComments() {
        return commentMapper.getBannedComments();
    }

    @Override
    public Object getCommentById(Integer id) {
        return commentMapper.getCommentById(id);
    }
}