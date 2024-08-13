package com.example.back.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SubscriptService {
    void addSubscription(Integer userId, Integer targetId);

    // 根据 user_id 查询所有的 target_id
    List<Integer> getTargetIdsByUserId(Integer userId);

    // 新增删除订阅的方法
    boolean deleteSubscript(int userId, int targetId);
}