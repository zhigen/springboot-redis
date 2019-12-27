package com.zglu.redis.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zglu
 */
@Log4j2
@Service
public class TestService {

    @CacheEvict(value = "test", key = "#id")
    public void add(String id) {
        log.info("删除key=" + id);
    }

    @CacheEvict(value = "test", allEntries = true)
    public void del() {
        log.info("删除全部");
    }

    @Cacheable(value = "test", key = "#id")
    public Map<String, String> get(String id) {
        log.info("获取key=" + id);
        Map<String, String> map = new HashMap<>(16);
        map.put(id, id);
        return map;
    }
}
