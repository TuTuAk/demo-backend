package com.chunyang.demobackend.shiro;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;

public class CustomCacheManager implements CacheManager {

    private final RedisTemplate<String, Object> redisTemplate;

    public CustomCacheManager(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public <K, V> Cache<K, V> getCache(String s) {
        return new CustomCache<K, V>(redisTemplate);
    }
}