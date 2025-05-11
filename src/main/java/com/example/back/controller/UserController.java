package com.example.back.controller;

import com.example.back.pojo.LoginRequest;
import com.example.back.pojo.Result;
import com.example.back.pojo.User;
import com.example.back.pojo.UserUpdateRequest;
import com.example.back.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/SearchUserById/{id}")
    public Result search(@PathVariable Integer id) {
        List<User> userList = userService.search(id);
        for (User user : userList) {
            if (user.getAvatar() != null) {
                // 将 BLOB 转换为 Base64
                String base64Avatar = Base64.getEncoder().encodeToString(user.getAvatar());
                user.setAvatar_base64(base64Avatar);
                user.setAvatar(null); // 清空 BLOB 数据，避免传输
            }
        }
        return Result.success(userList);
    }

    @DeleteMapping("/DeleteUser/{id}")
    public Result delete(@PathVariable Integer id) {
        userService.delete(id);
        return Result.success();
    }

    @PostMapping("/NewUser")
    public Result add(@RequestBody User user) {
        try {
            // 处理头像数据
            if (user.getAvatar_base64() != null && !user.getAvatar_base64().isEmpty()) {
                user.setAvatar(Base64.getDecoder().decode(user.getAvatar_base64()));
            }
            
            userService.add(user);
            return Result.success("User created successfully");
        } catch (IllegalArgumentException e) {
            return Result.error(e.getMessage());
        } catch (Exception e) {
            return Result.error("Failed to create user: " + e.getMessage());
        }
    }

    @PutMapping("/editUser")
    public Result update(@RequestBody User user) {
        userService.update(user);
        return Result.success();
    }

    //根据用户名查找用户
    @GetMapping("/SearchUserByUsername")
    public Result searchByUsername(@RequestParam String username) {
        List<User> userList = userService.searchByUsername(username);
        for (User user : userList) {
            if (user.getAvatar() != null) {
                String base64Avatar = Base64.getEncoder().encodeToString(user.getAvatar());
                user.setAvatar_base64(base64Avatar);
                user.setAvatar(null);
            }
        }
        return Result.success(userList);
    }

    @PostMapping("/login")
    public Result verify(@RequestBody LoginRequest loginRequest) {
        String account = loginRequest.getAccount();
        String password = loginRequest.getPassword();
        User user = userService.verify(password, account);
        if (user != null) {
            if (user.getAvatar() != null) {
                String base64Avatar = Base64.getEncoder().encodeToString(user.getAvatar());
                user.setAvatar_base64(base64Avatar);
                user.setAvatar(null);
            }
            return Result.success(user);
        } else {
            return Result.error("密码或者账号错误！请重新输入");
        }
    }

    // 新增接口：获取所有 is_banned 为 1 的用户数据
    @GetMapping("/getBannedUsers")
    public Result getBannedUsers() {
        List<User> bannedUsers = userService.getBannedUsers();
        for (User user : bannedUsers) {
            if (user.getAvatar() != null) {
                String base64Avatar = Base64.getEncoder().encodeToString(user.getAvatar());
                user.setAvatar_base64(base64Avatar);
                user.setAvatar(null);
            }
        }
        return !bannedUsers.isEmpty() ? Result.success(bannedUsers) : Result.error("Banned users not found");
    }

    // 新增接口：禁用用户
    @PostMapping("/banUser")
    public Result banUser(@RequestParam Integer userId, @RequestParam(defaultValue = "7") Integer banDays) {
        userService.banUser(userId, banDays);
        return Result.success("User banned successfully for " + banDays + " days");
    }

    // 新增接口：解封用户
    @PostMapping("/unbanUser")
    public Result unbanUser(@RequestParam Integer userId) {
        userService.unbanUser(userId);
        return Result.success("User unbanned successfully and ban period cleared");
    }

    @PutMapping("/updateUserInfo")
    public Result updateUserInfo(@RequestBody UserUpdateRequest request) {
        if (request.getId() == null) {
            return Result.error("User ID is required");
        }
        
        List<User> users = userService.search(request.getId());
        if (users == null || users.isEmpty()) {
            return Result.error("User not found");
        }
        
        User user = users.get(0);
        
        if (request.getUsername() != null) user.setUsername(request.getUsername());
        if (request.getAvatar() != null) {
            // 将 Base64 转换为 byte[]
            user.setAvatar(Base64.getDecoder().decode(request.getAvatar()));
        }
        if (request.getEmail() != null) user.setEmail(request.getEmail());
        if (request.getGender() != null) user.setGender(request.getGender());
        if (request.getIntroduction() != null) user.setIntroduction(request.getIntroduction());
        
        userService.update(user);
        return Result.success();
    }

    // 获取用户订阅列表
    @GetMapping("/getSubscriptions/{userId}")
    public Result getSubscriptions(@PathVariable Integer userId) {
        List<Integer> subscriptions = userService.getSubscriptions(userId);
        return Result.success(subscriptions);
    }
}