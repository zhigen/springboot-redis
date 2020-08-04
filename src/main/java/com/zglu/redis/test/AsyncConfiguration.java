package com.zglu.redis.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @author zglu
 */
@Configuration
@EnableAsync
public class AsyncConfiguration {

    @Bean
    public Executor executor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程数：执行任务的线程数
        executor.setCorePoolSize(50);
        //最大线程数：执行任务的最大线程数，只有在缓冲队列满了之后才会申请超过核心线程数的线程
        executor.setMaxPoolSize(500);
        //缓冲队列：用来缓冲执行任务的线程队列
        executor.setQueueCapacity(100000);
        //线程空闲时间：当线程空闲时间达到keepAliveTime时，线程会退出
        executor.setKeepAliveSeconds(5);
        //线程名的前缀
        executor.setThreadNamePrefix("executor-");
        executor.initialize();
        return executor;
    }

}
