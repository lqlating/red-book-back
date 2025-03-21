package com.example.back.service.impl;

import com.example.back.mapper.UserMapper;
import com.example.back.pojo.User;
import com.example.back.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> search(Integer id) {
        return userMapper.search(id);
    }

    @Override
    public void delete(Integer id) {
        userMapper.delete(id);
    }

    @Override
    public void add(User user) {
        userMapper.insert(user);
    }

    @Override
    public void update(User user) {
        userMapper.update(user);
    }

    @Override
    public User verify(String password, String account) {
        return userMapper.verify(password, account);
    }

    @Override
    public List<User> searchByUsername(String username) {
        return userMapper.searchByUsername(username);
    }

    // 新增方法：获取所有 is_banned 为 1 的用户数据
    @Override
    public List<User> getBannedUsers() {
        return userMapper.getBannedUsers();
    }

    // 新增方法：禁用用户
    @Override
    public void banUser(Integer userId) {
        User user = userMapper.search(userId).stream().findFirst().orElse(null);
        if (user != null) {
            user.setIsBanned(1);
            user.setBanUntil(LocalDateTime.now().plus(10, ChronoUnit.DAYS));
            userMapper.updateUser(user);
        }
    }

    // 新增方法：解封用户
    @Override
    public void unbanUser(Integer userId) {
        User user = userMapper.search(userId).stream().findFirst().orElse(null);
        if (user != null) {
            user.setIsBanned(0);
            user.setBanUntil(null);
            userMapper.updateUser(user);
        }
    }

    @Override
    public Object getUserById(Integer id) {
        return userMapper.getUserById(id);
    }
}