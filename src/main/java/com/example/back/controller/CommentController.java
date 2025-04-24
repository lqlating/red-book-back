package com.example.back.controller;

import com.example.back.pojo.Comment;
import com.example.back.pojo.Result;
import com.example.back.pojo.User;
import com.example.back.service.CommentService;
import com.example.back.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private ReportService reportService;

    @Operation(summary = "根据喜欢的数量获取评论", description = "返回指定文章ID的评论列表，按喜欢数量降序排列，且 is_banned = 0")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "评论获取成功",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Comment.class)) }),
            @ApiResponse(responseCode = "404", description = "评论未找到",
                    content = @Content)
    })
    @GetMapping("/getCommentBylikeCount/{article_id}")
    public Result getCommentBylikeCount(@PathVariable Integer article_id){
        System.out.println("get comment successfully");
        List<Comment> commentList = commentService.getCommentBylikeCount(article_id);
        return Result.success(commentList);
    }

    @Operation(summary = "根据发布时间获取评论", description = "返回指定文章ID的评论列表，按发布时间降序排列，且 is_banned = 0")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "评论获取成功",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Comment.class)) }),
            @ApiResponse(responseCode = "404", description = "评论未找到",
                    content = @Content)
    })
    @GetMapping("/getCommentByTime/{article_id}")
    public Result getCommentByTime(@PathVariable Integer article_id){
        System.out.println("get comment successfully");
        List<Comment> commentList = commentService.getCommentByTime(article_id);
        return Result.success(commentList);
    }

    @Operation(summary = "获取子评论数量", description = "返回指定评论ID的子评论数量，且 is_banned = 0")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "子评论数量获取成功",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Integer.class)) }),
            @ApiResponse(responseCode = "404", description = "子评论数量未找到",
                    content = @Content)
    })
    @GetMapping("/getReplyCountByCommentId/{comment_id}")
    public Result getReplyCountByCommentId(@PathVariable Integer comment_id) {
        System.out.println("get reply count successfully");
        int replyCount = commentService.getReplyCountByCommentId(comment_id);
        return Result.success(replyCount);
    }

    @Operation(summary = "根据根评论ID获取子评论", description = "返回指定根评论ID的所有子评论，按发布时间升序排列，且 is_banned = 0")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "子评论获取成功",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Comment.class)) }),
            @ApiResponse(responseCode = "404", description = "子评论未找到",
                    content = @Content)
    })
    @GetMapping("/getCommentsByParentId/{parent_id}")
    public Result getCommentsByParentId(@PathVariable Integer parent_id) {
        System.out.println("get comments successfully");
        List<Comment> commentList = commentService.getCommentsByParentId(parent_id);
        return Result.success(commentList);
    }

    @Operation(summary = "根据根评论ID获取子评论数量", description = "返回指定根评论ID的所有子评论数量，且 is_banned = 0")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "子评论数量获取成功",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Integer.class)) }),
            @ApiResponse(responseCode = "404", description = "子评论数量未找到",
                    content = @Content)
    })
    @GetMapping("/getCommentCountByParentId/{parent_id}")
    public Result getCommentCountByParentId(@PathVariable Integer parent_id) {
        System.out.println("get comment count successfully");
        int commentCount = commentService.getCommentCountByParentId(parent_id);
        return Result.success(commentCount);
    }

    @Operation(summary = "根据评论ID查找用户ID", description = "返回指定评论ID的用户ID列表，且 is_banned = 0")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "用户ID获取成功",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "404", description = "用户ID未找到",
                    content = @Content)
    })
    @GetMapping("/getUserByCommentId/{commentId}")
    public Result getUserByCommentId(@PathVariable Integer commentId) {
        List<User> users = commentService.findUserByCommentId(commentId);
        if (users.isEmpty()) {
            return Result.error("User ID not found for the specified comment ID.");
        }
        return Result.success(users);
    }

    @Operation(summary = "添加评论", description = "向指定文章添加评论，并设置初始值")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "评论添加成功",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Result.class)) }),
            @ApiResponse(responseCode = "400", description = "评论添加失败",
                    content = @Content)
    })
    @PostMapping("/addComment")
    public Result addComment(@RequestBody Comment comment) {
        // 设置初始值
        comment.setLike_count(0);
        comment.setStar_count(0);
        comment.setPublish_time(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        comment.setIs_banned(0); // 设置 is_banned 默认值为 0

        return commentService.addComment(comment);
    }

    @Operation(summary = "根据用户ID获取评论", description = "返回指定用户ID的所有评论，且 is_banned = 0")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "评论获取成功",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Comment.class)) }),
            @ApiResponse(responseCode = "404", description = "评论未找到",
                    content = @Content)
    })
    @GetMapping("/getCommentsByUserId/{userId}")
    public Result getCommentsByUserId(@PathVariable Integer userId) {
        System.out.println("get comments by userId successfully");
        List<Comment> commentList = commentService.getCommentsByUserId(userId);
        return Result.success(commentList);
    }

    // 根据评论ID查询评论
    @Operation(summary = "根据评论ID查询评论", description = "返回指定评论ID的评论列表，且 is_banned = 0")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "评论获取成功",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Comment.class)) }),
            @ApiResponse(responseCode = "404", description = "评论未找到",
                    content = @Content)
    })
    @GetMapping("/byCommentId/{commentId}")
    public Result findCommentsByCommentId(@PathVariable Integer commentId) {
        List<Comment> commentList = commentService.findCommentByCommentId(commentId);
        if (commentList.isEmpty()) {
            return Result.error("No comments found for the specified comment ID.");
        }
        return Result.success(commentList);
    }

    // 新增接口：获取所有 is_banned 为 1 的评论数据
    @Operation(summary = "获取所有 is_banned 为 1 的评论", description = "返回所有 is_banned 为 1 的评论列表")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "评论获取成功",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Comment.class)) }),
            @ApiResponse(responseCode = "404", description = "评论未找到",
                    content = @Content)
    })
    @GetMapping("/getBannedComments")
    public Result getBannedComments() {
        List<Comment> bannedComments = commentService.getBannedComments();

        if (!bannedComments.isEmpty()) {
            return Result.success(bannedComments);  // 返回评论数据
        } else {
            return Result.error("Banned comments not found");
        }
    }

    // 新增接口：解除评论的禁止状态
    @Operation(summary = "解除评论的禁止状态", description = "将指定评论ID的评论 is_banned 设置为 0，并从report表中删除相关举报")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "评论已解禁",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Result.class)) }),
            @ApiResponse(responseCode = "400", description = "操作失败",
                    content = @Content)
    })
    @PutMapping("/unbanComment/{commentId}")
    public Result unbanComment(@PathVariable Integer commentId) {
        // 1. 解除评论的禁止状态
        boolean success = commentService.unbanCommentById(commentId);
        
        if (success) {
            try {
                // 2. 从report表中删除相关举报数据
                reportService.deleteReportByContentTypeAndId("comment", commentId);
                return Result.success("Comment unbanned successfully and related reports deleted");
            } catch (Exception e) {
                return Result.success("Comment unbanned successfully but failed to delete related reports");
            }
        } else {
            return Result.error("Failed to unban comment");
        }
    }

    // 新增接口：将评论设为禁止状态
    @Operation(summary = "将评论设为禁止状态", description = "将指定评论ID的评论 is_banned 设置为 1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "评论已禁止",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Result.class)) }),
            @ApiResponse(responseCode = "400", description = "操作失败",
                    content = @Content)
    })
    @PutMapping("/banComment/{commentId}")
    public Result banComment(@PathVariable Integer commentId) {
        boolean success = commentService.banCommentById(commentId);
        if (success) {
            return Result.success("Comment banned successfully");
        } else {
            return Result.error("Failed to ban comment");
        }
    }
}