package com.zglu.redis.controller;

import com.zglu.redis.dao.User;
import com.zglu.redis.service.TestService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author zglu
 */
@RestController
@AllArgsConstructor
public class TestController {

    private final RedissonClient redissonClient;
    private final TestService testService;

    @PostMapping("/test")
    @ApiOperation("增")
    public User add(@RequestBody User user) {
        return testService.add(user);
    }

    @GetMapping("/user/{id}")
    @ApiOperation("查")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "id", value = "id", required = true),
    })
    public User get(@PathVariable String id) {
        return testService.get(id);
    }

    @GetMapping("/user")
    @ApiOperation("查")
    public Map<String, User> all() {
        return testService.all();
    }

    @PutMapping("/user")
    @ApiOperation("覆盖写入")
    public User put(@RequestBody User user) {
        return testService.put(user);
    }

    @DeleteMapping("/user/{id}")
    @ApiOperation("删")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "id", value = "id", required = true),
    })
    public void remove(@PathVariable String id) {
        testService.remove(id);
    }

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
