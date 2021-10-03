package com.enjoyu.admin.component.auth.jwt;

import com.enjoyu.admin.utils.JwtUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.Date;

/**
 * jwt验证成功刷新
 * @author enjoyu
 */
public class JwtRefreshSuccessHandler implements AuthenticationSuccessHandler {

    private static final int REFRESH_INTERVAL = 300;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        JwtAuthenticationToken token = (JwtAuthenticationToken) authentication;
        Date iat = token.getIat();
        Date delay = Date.from(Instant.now().minusSeconds(REFRESH_INTERVAL));
        if (delay.after(iat)) {
            String subject = token.getSubject();
            String newToken = JwtUtils.sign(subject);
            response.setHeader(JwtUtils.JWT_HTTP_HEADER, JwtUtils.JWT_TOKEN_HEADER + newToken);
        }
    }
}
