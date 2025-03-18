package com.example.back.service;

import com.example.back.pojo.Result;
import com.example.back.pojo.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    //    查询用户
    List<User> search(Integer id);

    void delete(Integer id);

    void add(User user);

    void update(User user);

    User verify(String password, String account);

    List<User> searchByUsername(String username);

    // 新增方法：获取所有 is_banned 为 1 的用户数据
    List<User> getBannedUsers();
}