package com.zglu.redis.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author zglu
 */
@RestController
public class TestController {

    @GetMapping("/info")
    public String info(HttpSession session) {
        return session.getId();
    }

    @GetMapping("/out")
    public void out(HttpServletResponse response) throws IOException {
        // 清除Cookie方式退出登录
        Cookie c = new Cookie("SESSION", "");
        c.setMaxAge(0);
        response.addCookie(c);
        response.sendRedirect("/info");
    }
}
