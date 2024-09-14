package com.example.back.mapper;

import com.example.back.pojo.Result;
import com.example.back.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM user_info WHERE id = #{id};\n")
    List<User> search(Integer id);

    @Delete("delete from user_info WHERE id = #{id} ")
    void delete(Integer id);

    @Insert("INSERT INTO user_info (username, password, avatar, email,id) VALUES (#{username}, #{password}, #{avatar}, #{email},#{id})")
    void insert(User user);


    @Update("")
    void update(User user);


    User verify(String password,String account);

    @Select("SELECT * FROM user_info WHERE username LIKE CONCAT('%', #{username}, '%')")
    List<User> searchByUsername(@Param("username") String username);
}
