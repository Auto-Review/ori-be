package org.example.autoreview.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class SchedulerConfig {

    private static final int POOL_SIZE = 3;

    @Bean
    public ThreadPoolTaskScheduler taskScheduler(){
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();

        taskScheduler.setPoolSize(POOL_SIZE);
        taskScheduler.setThreadNamePrefix("My Scheduler-");
        taskScheduler.initialize();

        return taskScheduler;
    }
}
