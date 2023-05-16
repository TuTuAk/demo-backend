package com.chunyang.demobackend.shiro;

import com.chunyang.demobackend.util.TokenUtil;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class CustomCache<K, V> implements Cache<K, V> {

    private static final String PREFIX_SHIRO_CACHE = "shiro:";

    private static final Integer ACCESS_TOKEN_EXPIRE_TIME = 30;

    private final RedisTemplate<String, Object> redisTemplate;

    public CustomCache(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    private String getKey(Object key) {
        return PREFIX_SHIRO_CACHE + TokenUtil.getUsernameFromToken(key.toString());
    }

    @Override
    public Object get(Object key) throws CacheException {
        return redisTemplate.opsForValue().get(this.getKey(key));
    }

    @Override
    public Object put(Object key, Object value) throws CacheException {
        try {
            redisTemplate
                    .opsForValue()
                    .set(this.getKey(key),
                            value,
                            ACCESS_TOKEN_EXPIRE_TIME,
                            TimeUnit.MINUTES);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Object remove(Object key) throws CacheException {
        return redisTemplate.delete(this.getKey(key));
    }

    @Override
    public void clear() throws CacheException {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Set<K> keys() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }
}