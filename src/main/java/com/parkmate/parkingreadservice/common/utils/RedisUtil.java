package com.parkmate.parkingreadservice.common.utils;

import io.lettuce.core.RedisException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RedisUtil<K,V> {

    private final RedisTemplate<K, V> redisTemplate;

    public RedisUtil(RedisTemplate<K, V> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void insert(K key, V value) {
        try {
            this.redisTemplate.opsForValue().set(key, value);
        } catch (Exception e) {
            throw new RedisException(e.getMessage());
        }
    }

    public Optional<V> select(K key) {
        try {
            V value = this.redisTemplate.opsForValue().get(key);
            return Optional.ofNullable(value);
        } catch (Exception e) {
            throw new RedisException(e.getMessage());
        }
    }
}
