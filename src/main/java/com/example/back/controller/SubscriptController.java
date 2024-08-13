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


    @DeleteMapping("/deleteSubscript/{userId}/{targetId}")
    public Result deleteSubscript(@PathVariable("userId") int userId, @PathVariable("targetId") int targetId) {
        boolean isDeleted = subscriptService.deleteSubscript(userId, targetId);
        if (isDeleted) {
            return Result.success("订阅删除成功");
        } else {
            return Result.error("删除订阅失败");
        }
    }
}
