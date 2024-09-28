package com.example.back.service.impl;

import com.example.back.mapper.SubscriptMapper;
import com.example.back.service.SubscriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptServiceImpl implements SubscriptService {
    @Autowired
    private SubscriptMapper subscriptMapper;

    @Override
    public void addSubscription(Integer userId, Integer targetId) {
        subscriptMapper.insertSubscription(userId, targetId);
    }

    @Override
    public List<Integer> getTargetIdsByUserId(Integer userId) {
        return subscriptMapper.findTargetIdsByUserId(userId);
    }

    @Override
    public boolean deleteSubscript(int userId, int targetId) {
        return subscriptMapper.deleteSubscript(userId, targetId) > 0;
    }

    @Override
    public List<Integer> getUserIdsByTargetId(Integer targetId) {
        return subscriptMapper.findUserIdsByTargetId(targetId);
    }

    @Override
    public int countSubscriptionsByTargetId(Integer userId) {
        return subscriptMapper.countSubscriptionsByTargetId(userId);
    }

    // 新增查询 user_id 等于传入 userId 的数据条数
    @Override
    public int countSubscriptionsByUserId(Integer userId) {
        return subscriptMapper.countSubscriptionsByUserId(userId);
    }
}
