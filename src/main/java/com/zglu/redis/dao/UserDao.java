package com.zglu.redis.dao;

import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zglu
 */
@Log4j2
@Component
public class UserDao {

    private static List<User> list = new ArrayList<>();

    @CacheEvict(value = "user", allEntries = true)
    public User add(User user) {
        list.add(user);
        return user;
    }

    @Cacheable(value = "user", key = "#id")
    public User get(Long id) {
        log.info(id + "穿透");
        return list.stream().filter(m -> id.equals(m.getId())).findFirst().orElse(null);
    }

    @Cacheable(value = "user", key = "'list'")
    public List<User> list() {
        log.info("list穿透");
        return list;
    }

    @CacheEvict(value = "user", allEntries = true)
    public User put(User old, User user) {
        old.put(user);
        return user;
    }

    @CacheEvict(value = "user", allEntries = true)
    public void remove(User user) {
        list.remove(user);
    }

}
