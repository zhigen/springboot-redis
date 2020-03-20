package com.zglu.redis.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalTime;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author zglu
 */
@RestController
@AllArgsConstructor
public class TestController {

    private final RedissonClient redissonClient;

    @GetMapping("/session")
    @ApiOperation("获取当前session")
    public String get(HttpServletRequest request) {
        return request.getSession().getId();
    }

    @DeleteMapping("/session")
    @ApiOperation("清除当前session")
    public void remove(HttpServletResponse response) {
        // 清除Cookie方式退出登录
        Cookie c = new Cookie("SESSION", "");
        c.setHttpOnly(true);
        c.setMaxAge(0);
        response.addCookie(c);
    }

    @GetMapping("/lock/{seconds}")
    @ApiOperation("同一session加锁")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "seconds", value = "调用时长", required = true),
    })
    public String lock(HttpServletRequest request, @PathVariable long seconds) throws InterruptedException {
        HttpSession session = request.getSession();
        RLock rLock = redissonClient.getLock("lock" + session.getId());
        if (rLock.isLocked()) {
            return "已被锁住";
        }
        rLock.lock(10, TimeUnit.SECONDS);
        try {
            Thread.sleep(seconds * 1000);
            Integer i = (Integer) session.getAttribute("temp");
            i = Optional.ofNullable(i).orElse(0);
            session.setAttribute("temp", ++i);
            return LocalTime.now() + "：第" + i + "次访问";
        } finally {
            rLock.unlock();
        }
    }

}
