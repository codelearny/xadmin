package com.enjoyu.admin.common.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class ScheduleTask {

    @Scheduled(fixedRate = 120000)
    public void fixedRate() {
        log.info("固定时间间隔---{}", System.currentTimeMillis());
    }

    /**
     * 周一到周五，0h0m2s开始，每隔12h执行一次
     */
    @Scheduled(cron = "2 0 0/12 * * MON-FRI")
    private void execWorkDayEvery12hours() {
        log.info("cron表达式---{}", LocalDateTime.now());
    }
}
