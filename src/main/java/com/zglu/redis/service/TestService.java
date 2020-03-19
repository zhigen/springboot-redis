package com.zglu.redis.service;

import com.zglu.redis.dao.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author zglu
 */
@Log4j2
@Service
public class TestService {

    private static Map<String, User> map = new HashMap<>();

    @CacheEvict(value = "test", key = "#user.id")
    public User add(User user) {
        map.put(user.getId()+"", user);
        return user;
    }

    @Cacheable(value = "test", key = "#id")
    public User get(String id) {
        log.info("穿透");
        return map.get(id);
    }

    @Cacheable(value = "test1", key = "'all'")
    public Map<String, User> all() {
        log.info("穿透");
        return map;
    }

    @CacheEvict(value = "test")
    public User put(User user) {
        map.put(user.getId()+"", user);
        return user;
    }

    @CacheEvict(value = {"test","test1"}, allEntries = true)
    public void remove(String id) {
        map.remove(id);
    }

}
