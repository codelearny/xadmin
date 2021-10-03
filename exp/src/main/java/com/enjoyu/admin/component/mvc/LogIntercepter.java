package com.enjoyu.admin.component.mvc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class LogIntercepter implements HandlerInterceptor {
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("{} -- {} == {}", request.getRequestURI(), response.getStatus(), handler);
        if (ex != null) {
            log.error("err:", ex);
        }
    }
}
