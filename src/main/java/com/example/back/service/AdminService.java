package com.example.back.service;

import org.springframework.stereotype.Service;

@Service
public interface AdminService {
    
    /**
     * 验证管理员账号和密码
     * @param account 账号
     * @param password 密码
     * @return 如果账号和密码匹配返回true，否则返回false
     */
    boolean verifyAdmin(String account, String password);
} 