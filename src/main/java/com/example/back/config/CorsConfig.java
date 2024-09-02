package com.example.back.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // 允许所有映射路径
                        .allowedOrigins("http://localhost:5173","http://localhost:5174") // 允许特定的来源
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 允许特定的请求方法
                        .allowedHeaders("*") // 允许所有请求头
                        .allowCredentials(true); // 允许发送Cookie
            }
        };
    }
}
