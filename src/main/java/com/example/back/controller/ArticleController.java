package com.example.back.controller;

import com.example.back.pojo.Article;
import com.example.back.pojo.Result;
import com.example.back.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @GetMapping("/FilterContent/{type}")
    public Result list(@PathVariable String type){
        System.out.println("recommend content");
        List<Article> articleList = articleService.list(type);
        return Result.success(articleList);
    }

    @PostMapping("/addLike/{articleID}")
    public void addLike(@PathVariable Integer articleID){

        System.out.println("add like");
        articleService.addLike(articleID);
    }

    @PostMapping("/subLike/{articleID}")
    public void subLike(@PathVariable Integer articleID){

        System.out.println("sub like");
        articleService.subLike(articleID);
    }

    @PostMapping("/addStar/{articleID}")
    public  void addStar(@PathVariable Integer articleID){
        System.out.println("add star");
        articleService.addStar(articleID);
    }

    @PostMapping("/subStar/{articleID}")
    public void subStar(@PathVariable Integer articleID){
        System.out.println("sub star");
        articleService.subStar(articleID);
    }

    //根据article_id查询文章
    @GetMapping("/getArticleById/{article_id}")
    public Result getArticleById(@PathVariable Integer article_id) {
        Article article = articleService.getArticleById(article_id);
        if (article != null) {
            return Result.success(article);
        } else {
            return Result.error("Article not found");
        }
    }


}
