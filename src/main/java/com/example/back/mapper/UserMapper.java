package com.example.back.mapper;

import com.example.back.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> search(Integer id);

    void delete(Integer id);

    void insert(User user);

    void update(User user);

    User verify(String password, String account);

    List<User> searchByUsername(@Param("username") String username);

    List<User> getBannedUsers();

    User getUserById(@Param("id") Integer id);

    List<Integer> getSubscriptions(Integer userId);
}