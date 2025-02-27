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
import java.util.Base64;
import java.util.List;
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
        List<Article> articleList = articleService.list(type);
        // 对于每篇文章，将 BLOB 格式的图片转换为 Base64 格式的字符串
        for (Article article : articleList) {
            if (article.getImg() != null) {
                String base64Image = Base64.getEncoder().encodeToString(article.getImg());
                article.setImg_url(base64Image);  // 设置 Base64 图片数据
                article.setImg(null);  // 清空 BLOB 数据，避免返回
            }
        }

        return Result.success(articleList);  // 返回带有 Base64 图片的文章列表
    }

    @Operation(summary = "Add like to an article", description = "Increases the like count for the specified article")
    @PostMapping("/addLike/{articleID}")
    public void addLike(@PathVariable Integer articleID) {
        articleService.addLike(articleID);
    }

    @Operation(summary = "Remove like from an article", description = "Decreases the like count for the specified article")
    @PostMapping("/subLike/{articleID}")
    public void subLike(@PathVariable Integer articleID) {
        articleService.subLike(articleID);
    }

    @Operation(summary = "Add star to an article", description = "Increases the star count for the specified article")
    @PostMapping("/addStar/{articleID}")
    public void addStar(@PathVariable Integer articleID) {
        articleService.addStar(articleID);
    }

    @Operation(summary = "Remove star from an article", description = "Decreases the star count for the specified article")
    @PostMapping("/subStar/{articleID}")
    public void subStar(@PathVariable Integer articleID) {
        articleService.subStar(articleID);
    }

    @PostMapping("/getArticlesByIds")
    public Result getArticlesByIds(@RequestBody List<Integer> articleIds) {
        List<Article> articles = articleService.getArticlesByIds(articleIds);

        // 将 BLOB 图片数据转换为 Base64 格式
        for (Article article : articles) {
            if (article.getImg() != null) {
                String base64Image = Base64.getEncoder().encodeToString(article.getImg());
                article.setImg_url(base64Image);
                article.setImg(null);  // 清空 BLOB 数据
            }
        }

        if (!articles.isEmpty()) {
            return Result.success(articles);  // 返回 Base64 编码后的图片数据
        } else {
            return Result.error("Articles not found");
        }
    }

    @Operation(summary = "Search articles by title or content", description = "Searches for articles whose title or content contains the specified keyword")
    @GetMapping("/SearchArticle")
    public Result searchByTitleOrContent(@RequestParam String keyword) {
        List<Article> articles = articleService.searchByTitleOrContent(keyword);

        // 转换图片为 Base64
        for (Article article : articles) {
            if (article.getImg() != null) {
                String base64Image = Base64.getEncoder().encodeToString(article.getImg());
                article.setImg_url(base64Image);
                article.setImg(null);
            }
        }

        return Result.success(articles);
    }

    @GetMapping("/getArticlesByAuthorId/{authorId}")
    public Result getArticlesByAuthorId(@PathVariable Integer authorId) {
        List<Article> articles = articleService.getArticlesByAuthorId(authorId);

        // 转换图片为 Base64
        for (Article article : articles) {
            if (article.getImg() != null) {
                String base64Image = Base64.getEncoder().encodeToString(article.getImg());
                article.setImg_url(base64Image);
                article.setImg(null);
            }
        }

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
        // 将 Base64 图片转换为字节数组
        if (article.getImg() != null) {
            byte[] imageBytes = Base64.getDecoder().decode(article.getImg());
            article.setImgData(imageBytes); // 设置为字节数组
            article.setImg(null); // 清空 Base64 数据，避免冗余
        }
        articleService.save(article);
        return Result.success("Article added successfully");
    }

    private String getRandomAddress() {
        String[] provinces = {"北京", "上海", "广东", "江苏", "浙江", "四川", "陕西", "山东", "湖北", "湖南"};
        Random random = new Random();
        return provinces[random.nextInt(provinces.length)];
    }
}
