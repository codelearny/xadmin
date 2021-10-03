package com.enjoyu.admin.component.auth.login;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * 参考 {@link FormLoginConfigurer}
 * @author enjoyu
 */
public class RestLoginConfigurer<H extends HttpSecurityBuilder<H>> extends
        AbstractAuthenticationFilterConfigurer<H, RestLoginConfigurer<H>, RestLoginUsernamePasswordAuthenticationFilter> {
    public RestLoginConfigurer() {
        super(new RestLoginUsernamePasswordAuthenticationFilter(), null);
    }

    @Override
    public void configure(H http) throws Exception {
        super.configure(http);
    }

    @Override
    protected RequestMatcher createLoginProcessingUrlMatcher(String loginProcessingUrl) {
        return null;
    }
}
