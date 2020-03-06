package com.zglu.redis;

import com.zglu.redis.service.TestService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author zglu
 */
@Log4j2
@ExtendWith(SpringExtension.class)
@SpringBootTest
class ApplicationTests {

    @Autowired
    private TestService testService;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(testService, "testService not be null");
        log.info("生成缓存");
        testService.get("1");
        testService.get("2");

        log.info("缓存取值");
        testService.get("1");
        testService.get("2");

        log.info("删除某key");
        testService.add("1");
        testService.get("1");
        testService.get("2");

        log.info("全部删除");
        testService.del();
        testService.get("1");
        testService.get("2");
    }

}
