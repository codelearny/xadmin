package com.enjoyu.admin.common.utils;

import java.time.Instant;
import java.time.temporal.TemporalUnit;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author enjoyu
 */
public abstract class DateUtil {
    /**
     * 计时器：从当前时刻向后推延
     *
     * @param unit  时间单位
     * @param delay 时长
     * @return 定时
     */
    public static Date hourglass(TemporalUnit unit, long delay) {
        Instant plus = Instant.now().plus(delay, unit);
        return Date.from(plus);
    }

    /**
     * 计时器：从当前时刻向前提前
     *
     * @param unit  时间单位
     * @param delay 时长
     * @return 定时
     */
    public static Date backIn(TemporalUnit unit, long delay) {
        Instant plus = Instant.now().minus(delay, unit);
        return Date.from(plus);
    }


    public static Date now() {
        return new Date();
    }
}
