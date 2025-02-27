package com.example.back.controller;

import com.example.back.pojo.LoginRequest;
import com.example.back.pojo.Result;
import com.example.back.pojo.User;
import com.example.back.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
//    @RequestMapping(value = "/SearchUser",method = RequestMethod.GET)
    @GetMapping("/SearchUserById/{id}")
    public Result search(@PathVariable Integer id){
        List<User> userList = userService.search(id);
        return Result.success(userList);
    }

    @DeleteMapping("/DeleteUser/{id}")
    public Result delete(@PathVariable Integer id){
        userService.delete(id);
        return Result.success();
    }

    @PostMapping("/NewUser")
    public Result add(@RequestBody User user){
        userService.add(user);
        return Result.success();
    }



    @PutMapping("/editUser")
    public  Result update(@RequestBody User user){
        userService.update(user);
        return Result.success();
    }

    //根据用户名查找用户
    @GetMapping("/SearchUserByUsername")
    public Result searchByUsername(@RequestParam String username) {
        List<User> userList = userService.searchByUsername(username);
        return Result.success(userList);
    }

    @PostMapping("/login")
    public Result verify(@RequestBody LoginRequest loginRequest){
        String account = loginRequest.getAccount();
        String password = loginRequest.getPassword();
        User user = userService.verify(password,account);
        System.out.println("login success");
        if(user!=null){
            return Result.success(user);
        }
        else{
            return Result.error("密码或者账号错误！请重新输入");
        }
    }
}
