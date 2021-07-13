package com.enjoyu.admin.component.auth.login;

import com.enjoyu.admin.utils.JwtUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录成功返回
 */
public class RestLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String token = JwtUtils.sign(authentication.getName());
        response.setHeader(JwtUtils.JWT_HTTP_HEADER, JwtUtils.JWT_TOKEN_HEADER + token);
    }
}
