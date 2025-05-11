package com.example.back.service.impl;

import com.example.back.mapper.AdminMapper;
import com.example.back.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public boolean verifyAdmin(String account, String password) {
        Integer count = adminMapper.verifyAdmin(account, password);
        return count != null && count > 0;
    }
} 