package com.example.back.service;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SubscriptService {
    void addSubscription(Integer userId, Integer targetId);

    // 根据 user_id 查询所有的 target_id
    List<Integer> getTargetIdsByUserId(Integer userId);

    // 新增删除订阅的方法
    boolean deleteSubscript(int userId, int targetId);

    List<Integer> getUserIdsByTargetId(Integer targetId);

    int countSubscriptionsByTargetId(Integer userId);

    // 新增方法：查询 user_id 等于传入的 userId 的数据条数
    int countSubscriptionsByUserId(Integer userId);
}
