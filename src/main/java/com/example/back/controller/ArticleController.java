package com.example.back.controller;

import com.example.back.pojo.Article;
import com.example.back.pojo.Result;
import com.example.back.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Operation(summary = "Get article by ID", description = "Retrieves an article by its unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Article retrieved successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Article.class)) }),
            @ApiResponse(responseCode = "404", description = "Article not found",
                    content = @Content)
    })
    @GetMapping("/getArticleById/{article_id}")
    public Result getArticleById(@PathVariable Integer article_id) {
        Article article = articleService.getArticleById(article_id);
        if (article != null) {
            return Result.success(article);
        } else {
            return Result.error("Article not found");
        }
    }

    @Operation(summary = "Search articles by title or content", description = "Searches for articles whose title or content contains the specified keyword")
    @GetMapping("/SearchArticle")
    public Result searchByTitleOrContent(@RequestParam String keyword) {
        List<Article> articles = articleService.searchByTitleOrContent(keyword);
        return Result.success(articles);
    }
}
