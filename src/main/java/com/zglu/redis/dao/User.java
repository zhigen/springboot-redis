package com.zglu.redis.dao;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author zglu
 */
@Data
public class User {
    private Long id;
    private String name;
    private LocalDateTime time = LocalDateTime.now();

    public void put(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.time = user.getTime();
    }
}
