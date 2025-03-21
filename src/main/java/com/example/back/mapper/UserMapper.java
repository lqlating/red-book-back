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

    @Insert("INSERT INTO user_info (username, password, avatar, email,id, ban_until, is_banned) VALUES (#{username}, #{password}, #{avatar}, #{email},#{id}, #{ban_until}, #{is_banned})")
    void insert(User user);

    @Update("UPDATE user_info SET username = #{username}, password = #{password}, avatar = #{avatar}, email = #{email}, ban_until = #{ban_until}, is_banned = #{is_banned} WHERE id = #{id}")
    void update(User user);

    User verify(String password, String account);

    @Select("SELECT * FROM user_info WHERE username LIKE CONCAT('%', #{username}, '%')")
    List<User> searchByUsername(@Param("username") String username);

    // 删除了Java接口中的getBannedUsers方法定义，改为只在XML中定义
    List<User> getBannedUsers();

    // 新增方法：更新用户信息
    void updateUser(User user);

    Object getUserById(Integer id);
}