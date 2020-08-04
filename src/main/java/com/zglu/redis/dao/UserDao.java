package com.zglu.redis.dao;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author zglu
 */
@Log4j2
@Component
@AllArgsConstructor
public class UserDao {
    private static final String VALUE = "user";
    private final RedisTemplate<String, List<User>> redisTemplate;

    private List<User> db() {
        return Optional.ofNullable(redisTemplate.opsForValue().get("db")).orElse(new ArrayList<>());
    }

    private void db(List<User> list) {
        redisTemplate.opsForValue().set("db", list);
    }

    @CacheEvict(value = VALUE, allEntries = true)
    public User add(User user) {
        List<User> list = db();
        list.add(user);
        db(list);
        return user;
    }

    @Cacheable(value = VALUE, keyGenerator = "antKeyGenerator")
    public User get(Long id) {
        log.info(id + "穿透");
        return db().stream().filter(m -> id.equals(m.getId())).findFirst().orElse(null);
    }

    @Cacheable(value = VALUE + "::60", keyGenerator = "antKeyGenerator")
    public List<User> get() {
        log.info("穿透");
        return db();
    }

    @CacheEvict(value = VALUE, allEntries = true)
    public User set(User user) {
        List<User> list = db();
        list.stream().filter(m -> m.getId().equals(user.getId())).forEach(m -> m.set(user));
        db(list);
        return user;
    }

    @CacheEvict(value = VALUE, allEntries = true)
    public void remove(User user) {
        List<User> list = db();
        list.remove(user);
        db(list);
    }

}
