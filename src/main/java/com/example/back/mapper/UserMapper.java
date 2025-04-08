package com.example.back.mapper;

import com.example.back.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM user_info WHERE id = #{id};\n")
    List<User> search(Integer id);

    @Delete("delete from user_info WHERE id = #{id} ")
    void delete(Integer id);

    @Insert("INSERT INTO user_info (username, password, avatar, email, id, ban_until, is_banned, account, gender, introduction) VALUES (#{username}, #{password}, #{avatar}, #{email}, #{id}, #{ban_until}, #{is_banned}, #{account}, #{gender}, #{introduction})")
    void insert(User user);

    void update(User user);

    User verify(String password, String account);

    @Select("SELECT * FROM user_info WHERE username LIKE CONCAT('%', #{username}, '%')")
    List<User> searchByUsername(@Param("username") String username);

    List<User> getBannedUsers();

    User getUserById(@Param("id") Integer id);
}