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
        // 验证必填字段
        if (user.getAccount() == null || user.getAccount().trim().isEmpty()) {
            throw new IllegalArgumentException("Account is required");
        }
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Password is required");
        }
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username is required");
        }

        // 检查账号是否已存在
        List<User> existingUsers = userMapper.searchByUsername(user.getAccount());
        if (!existingUsers.isEmpty()) {
            throw new IllegalArgumentException("Account already exists");
        }

        // 设置新用户的默认值
        if (user.getFans() == null) {
            user.setFans("0");
        }
        if (user.getIsBanned() == null) {
            user.setIsBanned(0);
        }
        user.setBanUntil(null);

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
            userMapper.update(user);
        }
    }

    // 新增方法：解封用户
    @Override
    public void unbanUser(Integer userId) {
        User user = userMapper.search(userId).stream().findFirst().orElse(null);
        if (user != null) {
            user.setIsBanned(0);
            user.setBanUntil(null);
            userMapper.update(user);
        }
    }

    @Override
    public Object getUserById(Integer id) {
        return userMapper.getUserById(id);
    }
}