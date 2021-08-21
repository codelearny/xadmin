package com.enjoyu.admin.web.filter;

import org.springframework.core.annotation.Order;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.Instant;

/**
 * @author enjoyu
 */
@Order(1)
@WebFilter(filterName = "logFilter", urlPatterns = "/*")
public class RequestLogFilter extends AbstractRequestLoggingFilter {
    @Override
    protected void beforeRequest(HttpServletRequest req, String s) {
        Instant startTime = Instant.now();
        req.setAttribute("startTime", startTime);
        logger.info(startTime + " -- " + s);
    }

    @Override
    protected void afterRequest(HttpServletRequest req, String s) {
        Instant startTime = (Instant) req.getAttribute("startTime");
        Instant end = Instant.now();
        logger.info(startTime + " -- 耗时 : " + Duration.between(startTime, end).getSeconds());
    }
}
