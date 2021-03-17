package com.enjoyu.admin.common.pool;

import org.springframework.boot.task.TaskExecutorCustomizer;
import org.springframework.boot.task.TaskSchedulerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.ThreadPoolExecutor;

import static org.springframework.scheduling.support.TaskUtils.LOG_AND_SUPPRESS_ERROR_HANDLER;

@EnableAsync
@EnableScheduling
@Configuration
public class ThreadPoolConfig {

    @Bean
    public TaskExecutorCustomizer taskExecutorCustomizer() {
        return taskExecutor -> taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
    }

    @Bean
    public TaskSchedulerCustomizer taskSchedulerCustomizer() {
        return taskScheduler -> {
            taskScheduler.setRemoveOnCancelPolicy(true);
            taskScheduler.setErrorHandler(LOG_AND_SUPPRESS_ERROR_HANDLER);
        };
    }
}
