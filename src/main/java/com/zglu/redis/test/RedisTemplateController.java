package com.zglu.redis.test;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author zglu
 */
@RestController
@RequestMapping("/redis-template")
@AllArgsConstructor
public class RedisTemplateController {
    private final TestService testService;
    private final RedisTemplate<String, Integer> redisTemplate;
    private final RedisTemplate<String, Integer> redisTemplateCommon;

    @PostMapping
    @ApiOperation("生成缓存")
    public void add() {
        redisTemplate.opsForValue().set("a", 1, 60, TimeUnit.MINUTES);
    }

    @PostMapping("/common")
    @ApiOperation("生成公共库缓存")
    public void addCommon() {
        redisTemplateCommon.opsForValue().set("a", 1);
    }

    @PostMapping("/set")
    @ApiOperation("生成set缓存")
    public Long addSet() {
        return redisTemplate.opsForSet().add("a", 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    }

    @GetMapping("/set")
    @ApiOperation("获取set缓存")
    public Integer getSet() {
        return redisTemplate.opsForSet().pop("a");
    }

    @GetMapping("/incr")
    @ApiOperation("高并发下incr")
    public Integer incr() {
        redisTemplate.opsForValue().set("i", 0);
        List<CompletableFuture<Void>> list = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            CompletableFuture<Void> completableFuture = testService.incr();
            list.add(completableFuture);
        }
        CompletableFuture.allOf(list.toArray(new CompletableFuture[0])).join();
        return redisTemplate.opsForValue().get("i");
    }

    @GetMapping("/pop")
    @ApiOperation("高并发下pop")
    public Integer pop() {
        redisTemplate.opsForSet().add("s", IntStream.range(1, 5001).boxed().toArray(Integer[]::new));
        List<CompletableFuture<Integer>> list = new ArrayList<>();
        int sum = 0;
        for (int i = 0; i < 10000; i++) {
            CompletableFuture<Integer> completableFuture = testService.pop();
            list.add(completableFuture);
            sum += Optional.ofNullable(completableFuture.join()).orElse(0);
        }
        CompletableFuture.allOf(list.toArray(new CompletableFuture[0])).join();
        return sum;
    }

}
