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

    private UserDao userDao;

    public User add(User user) {
        return userDao.add(user);
    }

    public User get(Long id) {
        return userDao.get(id);
    }

    public List<User> list() {
        return userDao.list();
    }

    public User put(User user) {
        User old = this.get(user.getId());
        if (old == null) {
            return this.add(user);
        }
        return userDao.put(old, user);
    }

    public void remove(Long id) {
        User user = this.get(id);
        userDao.remove(user);
    }

}
