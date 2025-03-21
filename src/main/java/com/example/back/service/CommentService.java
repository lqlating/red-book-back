package com.example.back.service;

import com.example.back.pojo.Comment;
import com.example.back.pojo.Result;
import com.example.back.pojo.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {
    List<Comment> getCommentBylikeCount(Integer article_id);

    List<Comment> getCommentByTime(Integer articleId);

    int getReplyCountByCommentId(Integer commentId);

    List<Comment> getCommentsByParentId(Integer parentId);

    int getCommentCountByParentId(Integer parentId);

    List<User> findUserByCommentId(Integer commentId);

    Result addComment(Comment comment);

    List<Comment> getCommentsByUserId(Integer userId);

    // 根据 comment_id 查找评论
    List<Comment> findCommentByCommentId(Integer commentId);

    // 新增方法：获取所有 is_banned 为 1 的评论数据
    List<Comment> getBannedComments();

    Object getCommentById(Integer id);
}