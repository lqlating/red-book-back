package com.example.back.controller;

import com.example.back.pojo.LikeStar;
import com.example.back.pojo.OperationRequest;
import com.example.back.pojo.Result;
import com.example.back.pojo.SearchRequest;
import com.example.back.service.LikeStarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/operation")
public class LikeStarController {

    @Autowired
    private LikeStarService likeStarService;

    // 添加点赞或收藏
    @PostMapping("/add")
    public Result addOperation(@RequestBody OperationRequest request) {
        Integer target_id = null;
        String content_type = null;

        if (request.getComment_id() != null) {
            target_id = request.getComment_id();
            content_type = "comment";
        } else if (request.getArticle_id() != null) {
            target_id = request.getArticle_id();
            content_type = "article";
        } else {
            return Result.error("Failure: No valid target ID provided");
        }

        int result = likeStarService.addLikeOrStar(request.getUser_id(), target_id, content_type, request.getOperation_type());
        return result > 0 ? Result.success() : Result.error("Failed to add " + request.getOperation_type());
    }

    // 删除点赞或收藏
    @PostMapping("/delete")
    public Result deleteOperation(@RequestBody OperationRequest request) {
        Integer target_id = null;
        String content_type = null;

        if (request.getComment_id() != null) {
            target_id = request.getComment_id();
            content_type = "comment";
        } else if (request.getArticle_id() != null) {
            target_id = request.getArticle_id();
            content_type = "article";
        } else {
            return Result.error("Failure: No valid target ID provided");
        }

        int result = likeStarService.deleteLikeOrStar(request.getUser_id(), target_id, content_type, request.getOperation_type());
        return result > 0 ? Result.success() : Result.error("Failed to delete " + request.getOperation_type());
    }


    // 查询点赞或收藏
    @PostMapping("/search")
    public Result searchListOperation(@RequestBody SearchRequest request) {
        // 使用传入的三个参数进行查询
        List<LikeStar> results = likeStarService.searchList(
                request.getUser_id(),
                request.getContent_type(),
                request.getOperation_type()
        );
        return results != null && !results.isEmpty() ? Result.success(results) : Result.error("No data found");
    }

    // 查询点赞或收藏的计数
    @PostMapping("/searchCount")
    public Result searchCount(@RequestBody OperationRequest request) {
        List<LikeStar> results = likeStarService.searchCount(
                request.getUser_id(),
                request.getComment_id(),
                request.getArticle_id(),
                request.getOperation_type()
        );
        return results != null && !results.isEmpty() ? Result.success(results) : Result.error("No data found");
    }
}
