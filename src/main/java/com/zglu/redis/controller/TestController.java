package com.zglu.redis.controller;

import lombok.AllArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
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

    @GetMapping("/info")
    public String info(HttpSession session) {
        return session.getId();
    }

    @GetMapping("/out")
    public void out(HttpServletResponse response) throws IOException {
        // 清除Cookie方式退出登录
        Cookie c = new Cookie("SESSION", "");
        c.setHttpOnly(true);
        c.setMaxAge(0);
        response.addCookie(c);
        response.sendRedirect("/info");
    }

    @GetMapping("/lock")
    public String lock(HttpSession session) throws InterruptedException {
        RLock rLock = redissonClient.getLock("lock" + session.getId());
        rLock.lock(5, TimeUnit.SECONDS);
        try {
            Thread.sleep(3000);
            Integer i = (Integer) session.getAttribute("temp");
            i = Optional.ofNullable(i).orElse(0);
            session.setAttribute("temp", ++i);
            return LocalTime.now() + "：第" + i + "次访问";
        } finally {
            rLock.unlock();
        }
    }
}
