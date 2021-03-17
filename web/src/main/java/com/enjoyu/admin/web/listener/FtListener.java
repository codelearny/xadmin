package com.enjoyu.admin.web.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@Order(1)
@WebListener
@Slf4j
public class FtListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        log.debug("sessionCreated {}", se.getSession().getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        log.debug("sessionDestroyed {}", se.getSession().getId());
    }
}
