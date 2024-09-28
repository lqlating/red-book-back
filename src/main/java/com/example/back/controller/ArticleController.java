package com.example.back.controller;

import com.example.back.pojo.Article;
import com.example.back.pojo.ArticleRequest;
import com.example.back.pojo.Result;
import com.example.back.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Operation(summary = "Filter content based on type", description = "Returns a list of articles filtered by type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Articles retrieved successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Article.class)) }),
            @ApiResponse(responseCode = "404", description = "Articles not found",
                    content = @Content)
    })
    @GetMapping("/FilterContent/{type}")
    public Result list(@PathVariable String type) {
        System.out.println("recommend content");
        List<Article> articleList = articleService.list(type);
        return Result.success(articleList);
    }

    @Operation(summary = "Add like to an article", description = "Increases the like count for the specified article")
    @PostMapping("/addLike/{articleID}")
    public void addLike(@PathVariable Integer articleID) {
        System.out.println("add like");
        articleService.addLike(articleID);
    }

    @Operation(summary = "Remove like from an article", description = "Decreases the like count for the specified article")
    @PostMapping("/subLike/{articleID}")
    public void subLike(@PathVariable Integer articleID) {
        System.out.println("sub like");
        articleService.subLike(articleID);
    }

    @Operation(summary = "Add star to an article", description = "Increases the star count for the specified article")
    @PostMapping("/addStar/{articleID}")
    public void addStar(@PathVariable Integer articleID) {
        System.out.println("add star");
        articleService.addStar(articleID);
    }

    @Operation(summary = "Remove star from an article", description = "Decreases the star count for the specified article")
    @PostMapping("/subStar/{articleID}")
    public void subStar(@PathVariable Integer articleID) {
        System.out.println("sub star");
        articleService.subStar(articleID);
    }

   
    @PostMapping("/getArticlesByIds")
    public Result getArticlesByIds(@RequestBody List<Integer> articleIds) {
        List<Article> articles = articleService.getArticlesByIds(articleIds);
        if (!articles.isEmpty()) {
            return Result.success(articles);
        } else {
            return Result.error("Articles not found");
        }
    }

    @Operation(summary = "Search articles by title or content", description = "Searches for articles whose title or content contains the specified keyword")
    @GetMapping("/SearchArticle")
    public Result searchByTitleOrContent(@RequestParam String keyword) {
        List<Article> articles = articleService.searchByTitleOrContent(keyword);
        return Result.success(articles);
    }
    

    @GetMapping("/getArticlesByAuthorId/{authorId}")
    public Result getArticlesByAuthorId(@PathVariable Integer authorId) {
        List<Article> articles = articleService.getArticlesByAuthorId(authorId);
        if (!articles.isEmpty()) {
            return Result.success(articles);
        } else {
            return Result.error("Articles not found");
        }
    }

    @Operation(summary = "Add a new article", description = "Inserts a new article into the database")
    @PostMapping("/add")
    public Result addArticle(@RequestBody ArticleRequest article) {
        // 设置默认值
        article.setLikeCount(0);
        article.setStarCount(0);
        article.setPublicationTime(LocalDate.now().toString()); // 获取当前日期
        article.setAddress(getRandomAddress()); // 获取随机地址

        articleService.save(article);
        return Result.success("Article added successfully");
    }

    private String getRandomAddress() {
        String[] provinces = {"北京", "上海", "广东", "江苏", "浙江", "四川", "陕西", "山东", "湖北", "湖南"};
        Random random = new Random();
        return provinces[random.nextInt(provinces.length)];
    }
}
