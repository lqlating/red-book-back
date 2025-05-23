package com.example.back.service;

import com.example.back.pojo.Comment;
import com.example.back.pojo.Result;
import com.example.back.pojo.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {
    List<Comment> getCommentBylikeCount(Integer article_id);

    List<Comment> getCommentByTime(Integer article_id);

    int getReplyCountByCommentId(Integer comment_id);

    List<Comment> getCommentsByParentId(Integer parent_id);

    int getCommentCountByParentId(Integer parent_id);

    List<User> findUserByCommentId(Integer commentId);

    Result addComment(Comment comment);

    List<Comment> getCommentsByUserId(Integer userId);

    List<Comment> findCommentByCommentId(Integer commentId);

    List<Comment> getBannedComments();

    /**
     * 将评论设为禁止状态
     * @param commentId 评论ID
     * @return 成功返回true，失败返回false
     */
    boolean banCommentById(Integer commentId);

    /**
     * 解除评论的禁止状态
     * @param commentId 评论ID
     * @return 成功返回true，失败返回false
     */
    boolean unbanCommentById(Integer commentId);

    /**
     * 删除评论
     * @param comment_id 评论ID
     * @return 成功返回true，失败返回false
     */
    boolean deleteComment(Integer comment_id);

    Object getCommentById(Integer id);
}