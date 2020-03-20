package com.zglu.redis.controller;

import com.zglu.redis.dao.User;
import com.zglu.redis.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zglu
 */
@RestController
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/user")
    @ApiOperation("增")
    public User add(@RequestBody User user) {
        return userService.add(user);
    }

    @GetMapping("/user/{id}")
    @ApiOperation("查")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "id", required = true),
    })
    public User get(@PathVariable Long id) {
        return userService.get(id);
    }

    @GetMapping("/user")
    @ApiOperation("查")
    public List<User> list() {
        return userService.list();
    }

    @PutMapping("/user")
    @ApiOperation("覆盖写入")
    public User put(@RequestBody User user) {
        return userService.put(user);
    }

    @DeleteMapping("/user/{id}")
    @ApiOperation("删")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "id", required = true),
    })
    public void remove(@PathVariable Long id) {
        userService.remove(id);
    }

}
