package com.enjoyu.admin.web.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @author enjoyu
 */
@Order(1)
@WebFilter
@Slf4j
public class FtFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.debug("filterChain {}", filterChain);
    }

    @Override
    public void init(FilterConfig filterConfig) {
        log.debug("filterConfig {}", filterConfig);
    }

    @Override
    public void destroy() {
        log.debug("{} destroy", FtFilter.class);
    }
}
