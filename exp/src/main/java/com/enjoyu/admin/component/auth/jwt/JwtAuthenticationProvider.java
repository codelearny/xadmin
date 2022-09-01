package com.enjoyu.admin.component.auth.jwt;


import com.enjoyu.admin.common.utils.DateUtil;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.NonceExpiredException;

/**
 * 参考{@link DaoAuthenticationProvider}
 */
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userService;


    public JwtAuthenticationProvider(UserDetailsService userService) {
        this.userService = userService;
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthenticationToken token = (JwtAuthenticationToken) authentication;
        try {
            token.verify();
        } catch (Exception e) {
            throw new BadCredentialsException("Bad JWT credentials", e);
        }
        if (token.getExp().before(DateUtil.now())) {
            throw new NonceExpiredException("Token expires");
        }
        String subject = token.getSubject();
        UserDetails user = userService.loadUserByUsername(subject);
        if (user == null) {
            throw new NonceExpiredException("Token expires");
        }
        return token.authenticated(user);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return JwtAuthenticationToken.class.isAssignableFrom(aClass);
    }
}
