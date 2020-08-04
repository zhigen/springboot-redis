package com.zglu.redis.service;

import com.zglu.redis.dao.User;
import com.zglu.redis.dao.UserDao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author zglu
 */
@Service
@AllArgsConstructor
public class UserService {
    private final UserDao userDao;

    public User add(User user) {
        return userDao.add(user);
    }

    public User get(Long id) {
        return userDao.get(id);
    }

    public List<User> get() {
        return userDao.get();
    }

    public User set(User user) {
        return userDao.set(user);
    }

    public void remove(Long id) {
        User user = this.get(id);
        userDao.remove(user);
    }

}
