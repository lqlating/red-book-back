package com.example.back.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AdminMapper {
    
    /**
     * 验证管理员账号和密码
     * @param account 账号
     * @param password 密码
     * @return 匹配的记录数
     */
    Integer verifyAdmin(@Param("account") String account, @Param("password") String password);
} 