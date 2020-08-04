package com.zglu.redis.test;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zglu
 */
@RestController
@RequestMapping("/session")
@AllArgsConstructor
public class SessionController {

    @GetMapping
    @ApiOperation("获取当前session")
    public String get(HttpServletRequest request) {
        return request.getSession().getId();
    }

    @DeleteMapping
    @ApiOperation("清除当前session")
    public void remove(HttpServletRequest request) {
        request.getSession().invalidate();
    }

    @DeleteMapping("/cookie")
    @ApiOperation("清除当前cookie")
    public void remove(HttpServletResponse response) {
        // 清除Cookie方式退出登录
        Cookie c = new Cookie("SESSION", "");
        c.setHttpOnly(true);
        c.setMaxAge(0);
        response.addCookie(c);
    }

}
