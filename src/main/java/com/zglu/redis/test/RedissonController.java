package com.zglu.redis.test;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author zglu
 */
@RestController
@RequestMapping("/redisson")
@AllArgsConstructor
public class RedissonController {
    private final RedissonClient redissonClient;

    @SneakyThrows
    @GetMapping
    @ApiOperation("每五秒处理一个请求")
    public String lock(HttpServletRequest request) {
        HttpSession session = request.getSession();
        RLock rLock = redissonClient.getLock("lock" + session.getId());
        rLock.lock(10, TimeUnit.SECONDS);
        try {
            Thread.sleep(5000);
            Integer i = (Integer) session.getAttribute("temp");
            i = Optional.ofNullable(i).orElse(0);
            session.setAttribute("temp", ++i);
            return LocalTime.now() + "：第" + i + "次访问";
        } finally {
            rLock.unlock();
        }
    }

}
