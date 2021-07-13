package com.enjoyu.admin.component.auth.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * rest登录过滤器，参考 {@link UsernamePasswordAuthenticationFilter}
 * @author enjoyu
 */
public class RestLoginUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/login", "POST");
    private final ObjectMapper mapper = new ObjectMapper();

    public RestLoginUsernamePasswordAuthenticationFilter() {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        Principal principal = mapper.readValue(request.getInputStream(), Principal.class);
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(principal.getUsername(), principal.getPassword());
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    @Getter
    @Setter
    private static class Principal {
        private String username;
        private String password;
    }
}
