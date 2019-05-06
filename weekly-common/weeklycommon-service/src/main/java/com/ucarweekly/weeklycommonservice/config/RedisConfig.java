package com.ucarweekly.weeklycommonservice.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author liaohong
 * @since 2018/11/23 14:14
 */
@Configuration
public class RedisConfig extends CachingConfigurerSupport {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setHashKeySerializer(new GenericJackson2JsonRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }

//    @Bean(value = "redisTemplate")
//    public RedisTemplate redisTemplate(RedisConnectionFactory connectionFactory){
//
//        RedisTemplate<Object, Object> redisTemplate=new RedisTemplate<Object, Object>();
////        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
//        redisTemplate.setConnectionFactory(connectionFactory);
//        redisTemplate.setValueSerializer(new StringRedisSerializer());
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
////        stringRedisTemplate.setConnectionFactory(connectionFactory);
////        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        return redisTemplate;
//
//    }
//
//
//    @Bean
//    public CacheManager cacheManager(RedisTemplate redisTemplate) {
//        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
////        cacheManager.setUsePrefix(true);
//        cacheManager.setDefaultExpiration(1800L);
//        return cacheManager;
//    }

}
