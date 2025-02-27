package com.example.back.controller;

import com.example.back.pojo.Comment;
import com.example.back.pojo.Result;
import com.example.back.pojo.User;
import com.example.back.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;
    @GetMapping("/getCommentBylikeCount/{article_id}")
    public Result getCommentBylikeCount(@PathVariable Integer article_id){
        System.out.println("get comment successfully");
        List<Comment> commentList = commentService.getCommentBylikeCount(article_id);
        return Result.success(commentList);
    }

    @GetMapping("/getCommentByTime{article_id}")
    public Result getCommentByTime(@PathVariable Integer article_id){
        System.out.println("get comment successfully");
        List<Comment> commentList = commentService.getCommentByTime(article_id);
        return Result.success(commentList);
    }

    @GetMapping("/getReplyCountByCommentId/{comment_id}")
    public Result getReplyCountByCommentId(@PathVariable Integer comment_id) {
        System.out.println("get reply count successfully");
        int replyCount = commentService.getReplyCountByCommentId(comment_id);
        return Result.success(replyCount);
    }

    @GetMapping("/getCommentsByParentId/{parent_id}")
    public Result getCommentsByParentId(@PathVariable Integer parent_id) {
        System.out.println("get comments successfully");
        List<Comment> commentList = commentService.getCommentsByParentId(parent_id);
        return Result.success(commentList);
    }

    @GetMapping("/getCommentCountByParentId/{parent_id}")
    public Result getCommentCountByParentId(@PathVariable Integer parent_id) {
        System.out.println("get comment count successfully");
        int commentCount = commentService.getCommentCountByParentId(parent_id);
        return Result.success(commentCount);
    }

    @GetMapping("/getUserByCommentId/{commentId}")
    public Result getUserByCommentId(@PathVariable Integer commentId) {
        List<User> userList = commentService.findUserByCommentId(commentId);
        return Result.success(userList);
    }

    @PostMapping("/addComment")
    public Result addComment(@RequestBody Comment comment) {
        // 设置初始值
        comment.setLike_count(0);
        comment.setStar_count(0);
        comment.setPublish_time(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        return commentService.addComment(comment);
    }

    @GetMapping("/getCommentsByUserId/{userId}")
    public Result getCommentsByUserId(@PathVariable Integer userId) {
        System.out.println("get comments by userId successfully");
        List<Comment> commentList = commentService.getCommentsByUserId(userId);
        return Result.success(commentList);
    }

    // 根据评论ID查询评论
    @GetMapping("/byCommentId/{commentId}")
    public Result findCommentsByCommentId(@PathVariable Integer commentId) {
        List<Comment> commentList = commentService.findCommentByCommentId(commentId);
        if (commentList.isEmpty()) {
            return Result.error("No comments found for the specified comment ID.");
        }
        return Result.success(commentList);
    }
}
