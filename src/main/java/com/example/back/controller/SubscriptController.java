package com.example.back.controller;

import com.example.back.pojo.Result;
import com.example.back.service.SubscriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SubscriptController {
    @Autowired
    private SubscriptService subscriptService;

    //关注
    @PostMapping("/addSubscription")
    public Result addSubscription(@RequestParam("userId") Integer userId, @RequestParam("targetId") Integer targetId) {
        try {
            subscriptService.addSubscription(userId, targetId);
            return Result.success();
        } catch (Exception e) {
            return Result.error("Failed to add subscription: " + e.getMessage());
        }
    }

    // 根据 user_id 查询所有的 target_id
    @GetMapping("/getTargetIds/{userId}")
    public Result getTargetIdsByUserId(@PathVariable Integer userId) {
        List<Integer> targetIds = subscriptService.getTargetIdsByUserId(userId);
        return Result.success(targetIds);
    }


    //取消关注
    @DeleteMapping("/deleteSubscript/{userId}/{targetId}")
    public Result deleteSubscript(@PathVariable("userId") int userId, @PathVariable("targetId") int targetId) {
        boolean isDeleted = subscriptService.deleteSubscript(userId, targetId);
        if (isDeleted) {
            return Result.success("订阅删除成功");
        } else {
            return Result.error("删除订阅失败");
        }
    }

    // 查询所有的 user_id，根据 target_id
    @GetMapping("/getUserIdsByTargetId/{targetId}")
    public Result getUserIdsByTargetId(@PathVariable Integer targetId) {
        try {
            List<Integer> userIds = subscriptService.getUserIdsByTargetId(targetId);
            return Result.success(userIds);
        } catch (Exception e) {
            return Result.error("Failed to fetch user IDs: " + e.getMessage());
        }
    }

    @GetMapping("/countSubscriptionsByTargetId/{userId}")
    public Result countSubscriptionsByTargetId(@PathVariable Integer userId) {
        try {
            int count = subscriptService.countSubscriptionsByTargetId(userId);
            return Result.success(count);
        } catch (Exception e) {
            return Result.error("Failed to count subscriptions: " + e.getMessage());
        }
    }

    // 新增接口：查询 user_id 等于传入的 userId 的数据条数
    @GetMapping("/countSubscriptionsByUserId/{userId}")
    public Result countSubscriptionsByUserId(@PathVariable Integer userId) {
        int count = subscriptService.countSubscriptionsByUserId(userId);
        return Result.success(count);
    }

}
