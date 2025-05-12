package com.example.back.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class MultiLevelCacheConfig {

    // Caffeine本地缓存配置 - 高并发首选，极快
    @Bean
    @Primary
    public CacheManager caffeineCacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        
        // 为重要接口配置本地缓存
        CaffeineCache articleListCache = buildCache("articleList", 500, 60);
        CaffeineCache articleCache = buildCache("article", 1000, 60); 
        CaffeineCache bookListCache = buildCache("bookList", 500, 60);
        CaffeineCache bookCache = buildCache("book", 1000, 60);
        CaffeineCache articleSearchCache = buildCache("articleSearch", 500, 30);
        
        cacheManager.setCaches(Arrays.asList(
                articleListCache, 
                articleCache,
                bookListCache,
                bookCache,
                articleSearchCache
        ));
        
        return cacheManager;
    }
    
    // 构建Caffeine缓存
    private CaffeineCache buildCache(String name, int maxSize, int expireSeconds) {
        return new CaffeineCache(name, 
                Caffeine.newBuilder()
                        .maximumSize(maxSize)
                        .expireAfterWrite(expireSeconds, TimeUnit.SECONDS)
                        .recordStats()
                        .build());
    }
    
    // Redis缓存配置 - 作为二级缓存，在本地缓存未命中时使用
    @Bean
    public CacheManager redisCacheManager(RedisConnectionFactory factory) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10))  // 缓存10分钟
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                .disableCachingNullValues(); // 禁止缓存null值，避免缓存穿透
                
        return RedisCacheManager.builder(factory)
                .cacheDefaults(config)
                .withCacheConfiguration("articleList", config.entryTtl(Duration.ofMinutes(5)))
                .withCacheConfiguration("bookList", config.entryTtl(Duration.ofMinutes(5)))
                .withCacheConfiguration("article", config.entryTtl(Duration.ofMinutes(10)))
                .withCacheConfiguration("book", config.entryTtl(Duration.ofMinutes(10)))
                .build();
    }
} 