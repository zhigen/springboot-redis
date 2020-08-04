package com.zglu.redis.controller;

import com.zglu.redis.dao.User;
import com.zglu.redis.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zglu
 */
@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    @ApiOperation("增")
    public User add(@RequestBody User user) {
        return userService.add(user);
    }

    @GetMapping("/{id}")
    @ApiOperation("查")
    public User get(@PathVariable Long id) {
        return userService.get(id);
    }

    @GetMapping
    @ApiOperation("查")
    public List<User> get() {
        return userService.get();
    }

    @PatchMapping
    @ApiOperation("改")
    public User set(@RequestBody User user) {
        return userService.set(user);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删")
    public void remove(@PathVariable Long id) {
        userService.remove(id);
    }

}
