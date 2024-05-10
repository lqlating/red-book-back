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

    User verify(String password,String account);
}
