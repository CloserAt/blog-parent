package com.hj.blog.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

//线程池配置类
@Configuration
@EnableAsync//开启线程池的注解
public class ThreadConfig {

    @Bean("taskExecutor")
    public Executor asyncServiceExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        //设置核心线程数
        threadPoolTaskExecutor.setCorePoolSize(5);
        //设置最大线程数
        threadPoolTaskExecutor.setMaxPoolSize(20);
        //配置工作队列大小
        threadPoolTaskExecutor.setQueueCapacity(Integer.MAX_VALUE);
        //配置线程存活时间
        threadPoolTaskExecutor.setKeepAliveSeconds(60);
        //设置默认线程名称
        threadPoolTaskExecutor.setThreadNamePrefix("Closer‘s Blog");
        //等待所有任务结束后关闭线程池
        threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        //执行初始化
        threadPoolTaskExecutor.initialize();

        return threadPoolTaskExecutor;
    }
}
