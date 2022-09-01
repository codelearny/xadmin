package com.enjoyu.admin.component.auth.jwt;

import com.enjoyu.admin.utils.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.*;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final RequestMatcher DEFAULT_REQUEST_HEADER_MATCHER = new RequestHeaderRequestMatcher(JwtUtils.JWT_HTTP_HEADER);
    private AuthenticationManager authenticationManager;
    private AuthenticationSuccessHandler successHandler = null;
    private AuthenticationFailureHandler failureHandler = null;
    private RequestMatcher requiresAuthenticationRequestMatcher;

    public JwtAuthenticationFilter() {
        this.requiresAuthenticationRequestMatcher = DEFAULT_REQUEST_HEADER_MATCHER;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        if (!requiresAuthentication(request)) {
            chain.doFilter(request, response);
            return;
        }
        Authentication authToken = null;
        AuthenticationException failed = null;
        try {
            String header = request.getHeader(JwtUtils.JWT_HTTP_HEADER);
            if (header == null) {
                failed = new InsufficientAuthenticationException("Authorization required!!!");
            }
            String token = JwtUtils.getTokenFromHttpHeader(header);
            if (token == null) {
                failed = new InsufficientAuthenticationException("JWT required!!!");
            }
            JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(token);
            authToken = this.getAuthenticationManager().authenticate(jwtAuthenticationToken);
            if (authToken == null) {
                failed = new InsufficientAuthenticationException("JWT auth failed!!!");
            }
        } catch (AuthenticationException e) {
            failed = e;
        }
        if (failed == null) {
            successfulAuthentication(request, response, authToken);
            chain.doFilter(request, response);
        } else {
            unsuccessfulAuthentication(request, response, failed);
        }
    }

    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response, Authentication authResult)
            throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(authResult);
        successHandler.onAuthenticationSuccess(request, response, authResult);
    }

    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response, AuthenticationException failed)
            throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        failureHandler.onAuthenticationFailure(request, response, failed);
    }

    protected AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationSuccessHandler getSuccessHandler() {
        return successHandler;
    }

    public void setSuccessHandler(AuthenticationSuccessHandler successHandler) {
        this.successHandler = successHandler;
    }

    public AuthenticationFailureHandler getFailureHandler() {
        return failureHandler;
    }

    public void setFailureHandler(AuthenticationFailureHandler failureHandler) {
        this.failureHandler = failureHandler;
    }

    public void setRequiresAuthenticationRequestMatcher(RequestMatcher requiresAuthenticationRequestMatcher) {
        this.requiresAuthenticationRequestMatcher = requiresAuthenticationRequestMatcher;
    }

    protected boolean requiresAuthentication(HttpServletRequest request) {
        return requiresAuthenticationRequestMatcher.matches(request);
    }
}
