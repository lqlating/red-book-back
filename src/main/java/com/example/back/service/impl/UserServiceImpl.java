package com.example.back.service.impl;

import com.example.back.mapper.UserMapper;
import com.example.back.pojo.Result;
import com.example.back.pojo.User;
import com.example.back.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> search(Integer id){
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
    public User verify(String password,String account) {
        return userMapper.verify(password,account);
    }

    @Override
    public List<User> searchByUsername(String username) {
        return userMapper.searchByUsername(username);
    }

}
