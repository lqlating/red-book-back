package com.example.back.controller;

import com.example.back.pojo.Admin;
import com.example.back.pojo.Result;
import com.example.back.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Operation(summary = "管理员登录", description = "验证管理员账号和密码")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "登录成功",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Result.class)) }),
            @ApiResponse(responseCode = "401", description = "登录失败，账号或密码错误",
                    content = @Content)
    })
    @PostMapping("/login")
    public Result login(@RequestBody Admin admin) {
        boolean isValid = adminService.verifyAdmin(admin.getAccount(), admin.getPassword());
        if (isValid) {
            return Result.success("登录成功");
        } else {
            return Result.error("账号或密码错误");
        }
    }
} 