package com.parkmate.parkingreadservice.common.utils;

import io.lettuce.core.RedisException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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

    public List<V> multiSelect(List<K> keys) {
        if (keys == null || keys.isEmpty()) {
            return Collections.emptyList();
        }
        try {
            List<V> values = this.redisTemplate.opsForValue().multiGet(keys);
            return values.stream()
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RedisException(e.getMessage());
        }
    }

    public List<V> nullableMultiSelect(List<K> keys) {
        if (keys == null || keys.isEmpty()) {
            return Collections.emptyList();
        }
        try {
            return this.redisTemplate.opsForValue().multiGet(keys);
        } catch (Exception e) {
            throw new RedisException(e.getMessage());
        }
    }

    public void multiInsert(Map<K, V> keyValueMap) {
        if (keyValueMap == null || keyValueMap.isEmpty()) {
            return;
        }
        try {
            this.redisTemplate.opsForValue().multiSet(keyValueMap);
        } catch (Exception e) {
            throw new RedisException(e.getMessage());
        }
    }

    public boolean exists(K key) {
        try {
            return this.redisTemplate.hasKey(key);
        } catch (Exception e) {
            throw new RedisException(e.getMessage());
        }
    }

    public void delete(K key) {
        try {
            this.redisTemplate.delete(key);
        } catch (Exception e) {
            throw new RedisException(e.getMessage());
        }
    }

    public void rename(K oldKey, K newKey) {
        try {
            this.redisTemplate.rename(oldKey, newKey);
        } catch (Exception e) {
            throw new RedisException(e.getMessage());
        }
    }
}
