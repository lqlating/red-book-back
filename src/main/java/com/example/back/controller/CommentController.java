package com.example.back.controller;

import com.example.back.pojo.Comment;
import com.example.back.pojo.Result;
import com.example.back.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;
    @GetMapping("/getComment/{article_id}")
    public Result list(@PathVariable Integer article_id){
        System.out.println("get comment sucessfully");
        List<Comment> commentList = commentService.getComment(article_id);
        return Result.success(commentList);
    }
}
