package com.example.back.controller;

import com.example.back.service.CountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class CountController {
    @Autowired
    private CountService countService;
    @GetMapping("/getCommentCount/{article_id}")
    public int getCommentCount(@PathVariable Integer article_id) {
        System.out.println("get count successfully");
        return countService.getCommentCount(article_id);
    }

}
