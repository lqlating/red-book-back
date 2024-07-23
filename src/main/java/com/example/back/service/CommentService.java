package com.example.back.service;

import com.example.back.pojo.Comment;
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
}
