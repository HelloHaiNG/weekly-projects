package com.ucar.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author liaohong
 * @since 2018/11/23 14:10
 */
@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    public void put(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.expire(key, 12, TimeUnit.HOURS);
    }

    public void put(String key, Object value, long millis) {
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.expire(key, millis, TimeUnit.MILLISECONDS);
    }

    public void putForHash(String objectKey,String hkey,String value) {
        redisTemplate.opsForHash().put(objectKey,hkey,value);
    }

    public <T> T get(String key, Class<T> type) {
        return (T) redisTemplate.boundValueOps(key).get();
    }

    public void remove(String key) {
        redisTemplate.delete(key);
    }

    public boolean expire(String key, long millis) {
        return redisTemplate.expire(key, millis, TimeUnit.MILLISECONDS);
    }

    public boolean persist(String key) {
        return redisTemplate.hasKey(key);
    }

    public String getString(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    public Integer getInteger(String key) {
        return (Integer) redisTemplate.opsForValue().get(key);
    }

    public Long getLong(String key) {
        return (Long) redisTemplate.opsForValue().get(key);
    }

    public Date getDate(String key) {
        return (Date) redisTemplate.opsForValue().get(key);
    }
}
