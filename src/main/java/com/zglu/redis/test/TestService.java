package com.zglu.redis.test;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * @author zglu
 */
@Log4j2
@Service
@AllArgsConstructor
public class TestService {
    private final RedisTemplate<String, Integer> redisTemplate;

    @Async("executor")
    public CompletableFuture<Void> incr() {
        redisTemplate.opsForValue().increment("i", 1);
        return CompletableFuture.completedFuture(null);
    }

    @Async("executor")
    public CompletableFuture<Integer> pop() {
        Integer s = redisTemplate.opsForSet().pop("s");
        return CompletableFuture.completedFuture(s);
    }
}
