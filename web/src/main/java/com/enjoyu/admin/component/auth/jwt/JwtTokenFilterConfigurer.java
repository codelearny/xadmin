package com.enjoyu.admin.component.auth.jwt;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;

/**
 * 添加{@link JwtAuthenticationFilter}
 * 参考{@link AbstractAuthenticationFilterConfigurer}
 * @author enjoyu
 */
public class JwtTokenFilterConfigurer<T extends JwtTokenFilterConfigurer<T, B>, B extends HttpSecurityBuilder<B>> extends AbstractHttpConfigurer<T, B> {

    private final JwtAuthenticationFilter authFilter;

    public JwtTokenFilterConfigurer() {
        this.authFilter = new JwtAuthenticationFilter();
    }

    @Override
    public void configure(B http) {
        authFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        JwtAuthenticationFilter filter = postProcess(authFilter);
        http.addFilterBefore(filter, LogoutFilter.class);
    }

    public JwtTokenFilterConfigurer<T, B> successHandler(AuthenticationSuccessHandler handler) {
        authFilter.setSuccessHandler(handler);
        return this;
    }

    public JwtTokenFilterConfigurer<T, B> failureHandler(AuthenticationFailureHandler handler) {
        authFilter.setFailureHandler(handler);
        return this;
    }

}
