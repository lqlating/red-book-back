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
}
